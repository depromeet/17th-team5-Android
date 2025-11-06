package com.depromeet.team5.features.principledetail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.CreatePrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.DeletePrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.DeletePrincipleUseCase
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.features.principledetail.event.PrincipleDetailEvent
import com.depromeet.team5.features.principledetail.navigation.PrincipleDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
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

    private val _uiStateFlow =
        MutableStateFlow<HedgeUiState<MyPrincipleGroup>>(HedgeUiState.Loading())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<PrincipleDetailEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        getPrinciple()
    }

    private fun getPrinciple() {
        getPrincipleUseCase(
            savedStateHandle.toRoute<PrincipleDetail>().groupId
        ).asUiState()
            .onEach { _uiStateFlow.emit(it) }
            .launchIn(viewModelScope)
    }

    fun deletePrincipleGroup(groupId: Int) {
        viewModelScope.launch {
            deletePrincipleGroupUseCase(groupId)
                .baseCollect(
                    onSuccess = {
                        _eventFlow.emit(PrincipleDetailEvent.Finish)
                    },
                    onError = {
                        _eventFlow.emit(PrincipleDetailEvent.ShowErrorToast(it))
                    }
                )
        }
    }

    fun deletePrinciple(principleId: Int) {
        viewModelScope.launch {
            deletePrincipleUseCase(principleId)
                .baseCollect(
                    onSuccess = {
                        val currentState = _uiStateFlow.value

                        if (currentState is HedgeUiState.Success) {
                            val updatedPrinciples =
                                currentState.data.principles.filter { it.id != principleId }

                            _uiStateFlow.update {
                                currentState.copy(
                                    data = currentState.data.copy(
                                        principles = updatedPrinciples
                                    )
                                )
                            }
                        }
                    },
                    onError = {
                        _eventFlow.emit(PrincipleDetailEvent.ShowErrorToast(it))
                    }
                )
        }
    }

    fun createPrincipleGroup(orderType: OrderType) {
        viewModelScope.launch {
            (uiStateFlow.value as HedgeUiState.Success<MyPrincipleGroup>).run {
                createPrincipleGroupUseCase(data.copy(orderType = orderType))
                    .baseCollect(
                        onSuccess = {
                            _eventFlow.emit(PrincipleDetailEvent.FinishAndShowToast)
                        },
                        onError = {
                            _eventFlow.emit(PrincipleDetailEvent.ShowErrorToast(it))
                        }
                    )
            }
        }
    }
}
