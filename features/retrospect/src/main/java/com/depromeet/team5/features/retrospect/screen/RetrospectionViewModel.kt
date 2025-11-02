package com.depromeet.team5.features.retrospect.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.GetPrinciplesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn


@HiltViewModel(assistedFactory = RetrospectionViewModel.Factory::class)
class RetrospectionViewModel @AssistedInject constructor(
    private val getPrincipleUseCase: GetPrinciplesUseCase,
    @Assisted orderType: String
) : ViewModel() {

    val principleUiState = getPrincipleUseCase(orderType)
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HedgeUiState.Loading(emptyList())
        )

    @AssistedFactory
    interface Factory {
        fun create(orderType: String): RetrospectionViewModel
    }
}
