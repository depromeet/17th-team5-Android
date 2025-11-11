package com.depromeet.team5.features.newprinciples.screen.selectprinciples

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.newprinciples.screen.navigation.SelectPrinciple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


@HiltViewModel
class SelectPrinciplesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPrincipleGroupUseCase: GetPrincipleGroupUseCase
) : ViewModel() {

    val newPrinciples by hedgeState<List<String>>(emptyList())

    val myPrincipleGroupUiState = flow {
        emit(savedStateHandle.toRoute<SelectPrinciple>())
    }
        .transform { (id, list) ->
            newPrinciples.update { list }
            emit(id)
        }
        .flatMapLatest { id ->
            getPrincipleGroupUseCase(id)
        }
        .asUiState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HedgeUiState.Loading()
        )
}
