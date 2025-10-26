package com.depromeet.team5.features.retrospect.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.GetPrinciplesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class RetrospectionViewModel @Inject constructor(
    private val getPrincipleUseCase: GetPrinciplesUseCase
) : ViewModel() {

    val principleUiState = getPrincipleUseCase()
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HedgeUiState.Loading()
        )
}
