package com.depromeet.team5.core.domain.monad

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


fun <T> Flow<T>.asUiState(): Flow<HedgeUiState<T>> = map<T, HedgeUiState<T>> {
    HedgeUiState.Success(it)
}
    .onStart { emit(HedgeUiState.Loading()) }
    .catch { emit(HedgeUiState.Error(throwable = it)) }
