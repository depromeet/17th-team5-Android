package com.depromeet.team5.core.domain.monad


sealed class HedgeUiState<out T> {

    data class Loading<T>(val data: T? = null) : HedgeUiState<T>()

    data class Success<T>(val data: T) : HedgeUiState<T>()

    data class Error(
        val code: String? = null,
        val message: String? = null,
        val throwable: Throwable? = null
    ) : HedgeUiState<Nothing>()

}
