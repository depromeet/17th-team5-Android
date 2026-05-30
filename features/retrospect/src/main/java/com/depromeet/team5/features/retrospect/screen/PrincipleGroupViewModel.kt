package com.depromeet.team5.features.retrospect.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupsUseCase
import com.depromeet.team5.core.domain.usecase.GetSystemPrincipleUseCase
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.retrospect.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


@HiltViewModel()
class PrincipleGroupViewModel @Inject constructor(
    private val getPrincipleGroupsUseCase: GetPrincipleGroupsUseCase,
    private val getPrincipleGroupUseCase: GetPrincipleGroupUseCase,
    private val getSystemPrincipleUseCase: GetSystemPrincipleUseCase,
) : ViewModel() {

    val uiState by hedgeState<UiState<MyPrincipleGroup>>(UiState.Loading)


    fun getPrincipleGroups(orderType: OrderType) {
        combine(
            getDefaultPrincipleGroup(orderType),
            getPrincipleGroupsUseCase(orderType)
        ) { defaultPrincipleGroup, myPrincipleGroups ->
            defaultPrincipleGroup to myPrincipleGroups
        }
            .map<Pair<MyPrincipleGroup, List<MyPrincipleGroup>>, UiState<MyPrincipleGroup>> { (defaultPrincipleGroup, myPrincipleGroups) ->
                UiState.Success(defaultPrincipleGroup, myPrincipleGroups)
            }
            .onStart { emit(UiState.Loading) }
            .catch { emit(UiState.Error(it)) }
            .onEach {
                uiState.emit(it)
            }
            .launchIn(viewModelScope)
    }


    private fun getDefaultPrincipleGroup(orderType: OrderType) = getSystemPrincipleUseCase()
        .map { it.data.defaults }
        .flatMapLatest { list ->
            val idx = when (orderType) {
                OrderType.BUY -> 0
                OrderType.SELL -> 1
                else -> -1
            }

            val groupId = list.getOrNull(idx)?.id
                ?: error("Unsupported orderType: $orderType")
            getPrincipleGroupUseCase(groupId)
        }
}
