package com.depromeet.team5.features.principlegroupdetail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.model.FinishType
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.monad.BaseEvent
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.CreatePrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.DeletePrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.DeletePrincipleUseCase
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.principlegroupdetail.navigation.PrincipleGroupDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrincipleDetailViewModel @Inject constructor(
    private val getPrincipleUseCase: GetPrincipleGroupUseCase,
    private val deletePrincipleGroupUseCase: DeletePrincipleGroupUseCase,
    private val deletePrincipleUseCase: DeletePrincipleUseCase,
    private val createPrincipleGroupUseCase: CreatePrincipleGroupUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiState by hedgeState<HedgeUiState<MyPrincipleGroup>>(HedgeUiState.Loading())

    private val _eventFlow = MutableSharedFlow<BaseEvent<FinishType>>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        getPrinciple()
    }

    fun getPrinciple() {
        getPrincipleUseCase(
            savedStateHandle.toRoute<PrincipleGroupDetail>().groupId
        )
            .asUiState()
            .onEach { uiState.emit(it) }
            .launchIn(viewModelScope)
    }

    fun deletePrincipleGroup(groupId: Int) {
        viewModelScope.launch {
            deletePrincipleGroupUseCase(groupId)
                .baseCollect(
                    onSuccess = {
                        _eventFlow.emit(BaseEvent.Finish(FinishType.REMOVE))
                    },
                    onError = {
                        _eventFlow.emit(BaseEvent.Error(it))
                    }
                )
        }
    }

    fun deletePrinciple(principleId: Int) {
        viewModelScope.launch {
            deletePrincipleUseCase(principleId)
                .baseCollect(
                    onSuccess = {
                        val currentState = uiState.value

                        if (currentState is HedgeUiState.Success) {
                            val updatedPrinciples =
                                currentState.data.principles.filter { it.id != principleId }

                            uiState.update {
                                currentState.copy(
                                    data = currentState.data.copy(
                                        principles = updatedPrinciples
                                    )
                                )
                            }
                        }
                    },
                    onError = {
                        _eventFlow.emit(BaseEvent.Error(it))
                    }
                )
        }
    }

    fun createPrincipleGroup(orderType: OrderType) {
        viewModelScope.launch {
            (uiState.value as HedgeUiState.Success<MyPrincipleGroup>).run {
                createPrincipleGroupUseCase(data.copy(orderType = orderType))
                    .baseCollect(
                        onSuccess = {
                            _eventFlow.emit(BaseEvent.Finish(FinishType.CREATION))
                        },
                        onError = {
                            _eventFlow.emit(BaseEvent.Error(it))
                        }
                    )
            }
        }
    }
}
