package com.depromeet.team5.features.feedback


sealed class AiFeedbackState {

    data class Success(
        val feedback: String
    ) : AiFeedbackState()

    object Loading : AiFeedbackState()

    data class Failure(
        val throwable: Throwable
    ) : AiFeedbackState()

}
