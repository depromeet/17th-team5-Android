package com.depromeet.team5.features.feedback

import com.depromeet.team5.core.model.Principle


sealed class AiFeedbackState {

    data class Success(
        val summarize: String,
        val summarizeOfMarket: String,
        val principles: List<Principle>
    ) : AiFeedbackState()

    object Loading : AiFeedbackState()

    data class Error(
        val code: String,
        val message: String
    ) : AiFeedbackState()

    data class Failure(
        val throwable: Throwable
    ) : AiFeedbackState()

}
