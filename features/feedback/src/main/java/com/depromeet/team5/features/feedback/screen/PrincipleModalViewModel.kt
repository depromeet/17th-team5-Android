package com.depromeet.team5.features.feedback.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupsUseCase
import com.depromeet.team5.core.domain.usecase.GetSystemPrincipleUseCase
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.feedback.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrincipleModalViewModel @Inject constructor(
    private val getPrincipleGroupsUseCase: GetPrincipleGroupsUseCase,
    private val getPrincipleGroupUseCase: GetPrincipleGroupUseCase,
    private val systemPrincipleUseCase: GetSystemPrincipleUseCase
) : ViewModel() {

    val defaultPrincipleGroupState by hedgeState(MyPrincipleGroup.EMPTY)
    val principleGroupsState by hedgeState<List<MyPrincipleGroup>>(emptyList())

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun getDefaultPrincipleGroup(orderType: OrderType) {
        viewModelScope.launch {
            systemPrincipleUseCase()
                .map { it.data.defaults }
                .flatMapConcat {
                    when (orderType) {
                        OrderType.BUY -> getPrincipleGroupUseCase(it[0].id)
                        OrderType.SELL -> getPrincipleGroupUseCase(it[1].id)
                        OrderType.NONE -> error(ERROR_DEFAULT_PRINCIPLE_API)
                    }
                }
                .baseCollect(
                    onSuccess = {
                        defaultPrincipleGroupState.emit(it)
                    },
                    onError = {
                        _eventFlow.emit(Event.ShowErrorToast(it))
                    }
                )
        }
    }

    fun getPrincipleGroups(orderType: OrderType) {
        viewModelScope.launch {
            getPrincipleGroupsUseCase(orderType)
                .baseCollect(
                    onSuccess = {
                        principleGroupsState.update { it }
                    },
                    onError = { throwable ->
                        _eventFlow.emit(Event.ShowErrorToast(throwable))
                    }
                )
        }
    }


    companion object {

        const val ERROR_DEFAULT_PRINCIPLE_API = ("기본 원칙 호출 시에는 반드시 OrderType이 지정되어야 합니다.")
    }
}
