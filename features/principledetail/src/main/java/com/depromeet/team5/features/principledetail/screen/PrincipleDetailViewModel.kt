package com.depromeet.team5.features.principledetail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.ui.restartflow.restartStateIn
import com.depromeet.team5.features.principledetail.navigation.PrincipleDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject


@HiltViewModel
class PrincipleDetailViewModel @Inject constructor(
    getPrincipleUseCase: GetPrincipleGroupUseCase,
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


    fun restart() {
        uiStateFlow.restart()
    }
}
