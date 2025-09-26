package com.depromeet.team5.features.feedback


sealed class AiFeedbackUiState {

    data class Success(
        val summarize: String,
        val summarizeOfMarket: String,
        val principles: List<PrincipleState>
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
    val title: String,
    val content: String,
    val isAdd: Boolean
)