package com.depromeet.team5.features.feedback.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupsUseCase
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.feedback.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrincipleModalViewModel @Inject constructor(
    private val getPrincipleGroupsUseCase: GetPrincipleGroupsUseCase
) : ViewModel() {

    val principleGroupsFlow by hedgeState<List<MyPrincipleGroup>>(emptyList())

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun getPrincipleGroups(orderType: OrderType) {
        viewModelScope.launch {
            getPrincipleGroupsUseCase(orderType.name)
                .baseCollect(
                    onSuccess = {
                        principleGroupsFlow.update { it }
                    },
                    onError = { throwable ->
                        _eventFlow.emit(Event.ShowErrorToast(throwable))
                    }
                )
        }
    }
}
