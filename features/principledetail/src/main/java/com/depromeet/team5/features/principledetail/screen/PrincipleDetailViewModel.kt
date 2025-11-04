package com.depromeet.team5.features.principledetail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.DeletePrincipleGroupUseCase
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.restartflow.restartStateIn
import com.depromeet.team5.features.principledetail.event.PrincipleDetailEvent
import com.depromeet.team5.features.principledetail.navigation.PrincipleDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrincipleDetailViewModel @Inject constructor(
    getPrincipleUseCase: GetPrincipleGroupUseCase,
    private val deletePrincipleGroupUseCase: DeletePrincipleGroupUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiStateFlow = getPrincipleUseCase(
        savedStateHandle.toRoute<PrincipleDetail>().groupId
    )
        .asUiState()
        .restartStateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HedgeUiState.Loading()
        )

    private val _eventFlow = MutableSharedFlow<PrincipleDetailEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun restart() {
        uiStateFlow.restart()
    }

    fun deletePrincipleGroup(groupId: Int) {
        viewModelScope.launch {
            deletePrincipleGroupUseCase(groupId)
                .baseCollect(
                    onSuccess = {
                        _eventFlow.emit(PrincipleDetailEvent.Finish)
                    },
                    onError = {
                        _eventFlow.emit(PrincipleDetailEvent.ShowErrorToast())
                    }
                )
        }
    }
}
