package com.depromeet.team5.features.home

sealed class HomeUiState {

    data class Success(
        val percentage:Int,
        val hedge: Int,
        val bronze: Int,
        val silver: Int,
        val gold: Int,
    ): HomeUiState()

    object Loading: HomeUiState()

    data class Error(
        val code: String,
        val message: String
    ): HomeUiState()

    data class Failure(
        val throwable: Throwable
    ): HomeUiState()
}
