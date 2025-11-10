package com.depromeet.team5.features.principlemodification.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.usecase.AddPrincipleUseCase
import com.depromeet.team5.core.domain.usecase.ModifyPrincipleUseCase
import com.depromeet.team5.core.navigation.PrincipleModificationType
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.principlemodification.event.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = PrincipleModificationViewModel.Factory::class)
class PrincipleModificationViewModel @AssistedInject constructor(
    private val modifyPrincipleUseCase: ModifyPrincipleUseCase,
    private val addPrincipleUseCase: AddPrincipleUseCase,
    @Assisted private val principleId: Int?,
    @Assisted myPrincipleGroup: MyPrincipleGroup?,
    @Assisted modificationType: PrincipleModificationType?
) : ViewModel() {

    val groupNameStateFlow by hedgeState<String?>(null)
    val principleStateFlow by hedgeState<String?>(null)
    val contentStateFlow by hedgeState<String?>(null)

    private val _eventFlow = MutableSharedFlow<Event>(extraBufferCapacity = 1)
    val eventFlow: SharedFlow<Event> = _eventFlow.asSharedFlow()

    private lateinit var myPrincipleGroup: MyPrincipleGroup
    private lateinit var modificationType: PrincipleModificationType


    init {
        if (myPrincipleGroup != null && modificationType != null) {
            this.myPrincipleGroup = myPrincipleGroup
            this.modificationType = modificationType
        } else {
            _eventFlow.tryEmit(
                Event.ShowErrorToast(NullPointerException(ERROR_MESSAGE))
            )
        }

        when (modificationType) {
            PrincipleModificationType.ADD -> {
                groupNameStateFlow.update { myPrincipleGroup?.groupName }
            }
            PrincipleModificationType.MODIFY -> {
                groupNameStateFlow.update { myPrincipleGroup?.groupName }

                principleId?.let { id ->
                    principleStateFlow.update { myPrincipleGroup?.principles?.find { it.id == id }?.principle }
                    contentStateFlow.update { myPrincipleGroup?.principles?.find { it.id == id }?.description }
                }

            }
            null -> {
                _eventFlow.tryEmit(Event.ShowErrorToast(Throwable(ERROR_TOAST_MESSAGE)))
            }
        }
    }

    fun modifyPrinciple() {
        viewModelScope.launch {
            combine(
                principleStateFlow.stateFlow,
                contentStateFlow.stateFlow
            ) { principle, description -> principle to description }
                .mapNotNull { (principle, description) ->
                    when {
                        principle != null && description != null -> principle to description
                        else -> null
                    }
                }
                .flatMapLatest { (principle, description) ->
                    when (modificationType) {
                        PrincipleModificationType.ADD -> {
                            addPrincipleUseCase(
                                groupId = myPrincipleGroup.id,
                                principle = principle,
                                description = description
                            )
                        }
                        PrincipleModificationType.MODIFY -> {
                            principleId?.let { id ->
                                modifyPrincipleUseCase(
                                    principleId = id,
                                    principle = principle,
                                    description = description
                                )
                            } ?: emptyFlow()
                        }
                    }
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


    @AssistedFactory
    interface Factory {

        fun create(
            principleId: Int?,
            myPrincipleGroup: MyPrincipleGroup?,
            modificationType: PrincipleModificationType?
        ): PrincipleModificationViewModel
    }

    companion object {

        private const val ERROR_MESSAGE = "MyPrincipleGroup 혹은 ModificationType이 null입니다."
        private const val ERROR_TOAST_MESSAGE = "예상하지 못한 에러가 발생했습니다\n잠시 후 다시 시도해주세요"
    }
}
