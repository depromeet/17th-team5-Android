package com.depromeet.team5.features.principlemodification.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.features.principlemodification.navigation.PrincipleModification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class PrincipleModificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiStateFlow = flow {
        emit(savedStateHandle.toRoute<PrincipleModification>())
    }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HedgeUiState.Loading()
        )


}
