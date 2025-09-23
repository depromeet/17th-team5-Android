package com.depromeet.team5.features.search

sealed interface UiState<out T> {
    data object Empty: UiState<Nothing>

    data object Loading: UiState<Nothing>

    data class Results<T>(val data: T): UiState<T>

    data object Error: UiState<Nothing>

    data class Recents<T>(val data: T): UiState<T>
}