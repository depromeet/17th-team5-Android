package com.depromeet.team5.features.feedback


sealed class AiFeedbackUiState {

    data class Success(
        val retrospectionId: Int,
        val symbol: String,
        val price: Long,
        val volume: Int,
        val orderType: String,
        val badge: String,
        val principleCheckSummary: PrincipleState,
        val keep: List<String>,
        val fix: List<String>,
        val next: List<String>
    ) : AiFeedbackUiState()

    object Loading : AiFeedbackUiState()

    data class Error(
        val code: String,
        val message: String
    ) : AiFeedbackUiState()

    data class Failure(
        val throwable: Throwable
    ) : AiFeedbackUiState()

}

data class PrincipleState(
    val keptCount: Int,
    val neutralCount: Int,
    val notKeptCount: Int
)