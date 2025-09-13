package com.depromeet.team5.features.search

sealed interface UiState<out T> {
    data object Empty: UiState<Nothing>

    data object Loading: UiState<Nothing>

    data class Success<T>(val data: T): UiState<T>

    data class Error(val message: String): UiState<Nothing>

    data class Default<T>(val data: T): UiState<T>
}