package com.depromeet.team5.features.principlemodification.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.ModifyPrincipleUseCase
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.principlemodification.event.Event
import com.depromeet.team5.features.principlemodification.navigation.PrincipleModification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrincipleModificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val modifyPrincipleUseCase: ModifyPrincipleUseCase,
) : ViewModel() {

    val principleIdStateFlow by hedgeState<Int?>(null)

    val groupNameStateFlow by hedgeState<String?>(null)
    val principleStateFlow by hedgeState<String?>(null)
    val contentStateFlow by hedgeState<String?>(null)

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow: SharedFlow<Event> = _eventFlow.asSharedFlow()


    init {
        flow {
            emit(savedStateHandle.toRoute<PrincipleModification>())
        }
            .asUiState()
            .onEach { state ->
                if (state is HedgeUiState.Success<PrincipleModification>) {
                    principleIdStateFlow.update { state.data.principleId }
                    groupNameStateFlow.update { state.data.groupName }
                    principleStateFlow.update { state.data.principle }
                    contentStateFlow.update { state.data.description }
                }
            }
            .catch { _eventFlow.emit(Event.ShowErrorToast(Throwable(ERROR_TOAST_MESSAGE))) }
            .launchIn(viewModelScope)
    }

    fun modifyPrinciple() {
        viewModelScope.launch {
            combine(
                principleIdStateFlow.stateFlow,
                principleStateFlow.stateFlow,
                contentStateFlow.stateFlow
            ) { id, principle, content -> Triple(id, principle, content) }
                .mapNotNull { (id, principle, content) ->
                    if (id != null && principle != null && content != null) {
                        Triple(id, principle, content)
                    } else {
                        error(ERROR_MESSAGE)
                    }
                }
                .flatMapLatest { (id, principle, content) ->
                    modifyPrincipleUseCase(
                        principleId = id,
                        principle = principle,
                        description = content
                    )
                }
                .baseCollect(
                    onSuccess = {
                        _eventFlow.emit(Event.Complete)
                    },
                    onError = {
                        _eventFlow.emit(Event.ShowErrorToast(it))
                    }
                )
        }
    }

    fun addPrinciple() {
//        viewModelScope.launch {
//            when (val value = uiStateFlow.value) {
//                is HedgeUiState.Success<PrincipleModification> -> {
//                    value.data.groupName?.let { groupId ->
//
//                    }
//                }
//
//                else -> {}
//            }
//        }
    }

    companion object {
        private const val ERROR_MESSAGE = "principleId, principle, content must not be null"
        private const val ERROR_TOAST_MESSAGE = "예상하지 못한 에러가 발생했습니다\n잠시 후 다시 시도해주세요"
    }
}
