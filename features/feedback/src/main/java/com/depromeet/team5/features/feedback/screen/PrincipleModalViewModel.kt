package com.depromeet.team5.features.feedback.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupsUseCase
import com.depromeet.team5.core.domain.usecase.GetSystemPrincipleUseCase
import com.depromeet.team5.core.ui.lazy.hedgeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrincipleModalViewModel @Inject constructor(
    private val getPrincipleGroupsUseCase: GetPrincipleGroupsUseCase,
    private val getPrincipleGroupUseCase: GetPrincipleGroupUseCase,
    private val getSystemPrincipleUseCase: GetSystemPrincipleUseCase
) : ViewModel() {

    val uiState by hedgeState<HedgeUiState<Pair<MyPrincipleGroup, List<MyPrincipleGroup>>>>(
        HedgeUiState.Loading()
    )


    fun getPrincipleGroups(orderType: OrderType) {
        viewModelScope.launch {
            combine(
                getDefaultPrincipleGroup(orderType),
                getPrincipleGroupsUseCase(orderType)
            ) { defaultPrincipleGroup, myPrincipleGroups ->
                defaultPrincipleGroup to myPrincipleGroups
            }
                .asUiState()
                .collect {
                    uiState.emit(it)
                }
        }
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
