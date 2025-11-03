package com.depromeet.team5.features.home

sealed class UserStatsUiState {

    data class Success(
        val percentage:Int,
        val hedge: Int,
        val bronze: Int,
        val silver: Int,
        val gold: Int,
    ): UserStatsUiState()

    object Loading: UserStatsUiState()

    data class Error(
        val code: String,
        val message: String
    ): UserStatsUiState()

    data class Failure(
        val throwable: Throwable
    ): UserStatsUiState()
}
