package com.depromeet.team5.features.retrospect.state


sealed class UiState<out T> {

    data class Success<T>(
        val defaultPrincipleGroup: T,
        val myPrincipleGroups: List<T>
    ) : UiState<T>()

    data class Error(
        val throwable: Throwable? = null
    ) : UiState<Nothing>()

    object Loading : UiState<Nothing>()

}