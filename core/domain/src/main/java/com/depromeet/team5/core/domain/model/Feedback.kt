package com.depromeet.team5.core.domain.model


data class Feedback(
    val code: String,
    val message: String,
    val summarize: String,
    val summarizeOfMarket: String,
    val principles: List<Principle>
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
