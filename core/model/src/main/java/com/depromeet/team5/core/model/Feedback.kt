package com.depromeet.team5.core.model

import androidx.compose.runtime.Stable


@Stable
data class Feedback(
    val code: String,
    val message: String,
    val summarize: String,
    val summarizeOfMarket: String,
    val principles: List<FeedbackPrinciple>
) {


    companion object {

        val EMPTY = Feedback(
            code = "",
            message = "",
            summarize = "",
            summarizeOfMarket = "",
            principles = emptyList()
        )
    }
}
