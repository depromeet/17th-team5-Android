package com.depromeet.team5.core.domain.model


data class FeedbackEntity(
    val code: String,
    val message: String,
    val summarize: String,
    val summarizeOfMarket: String,
    val principles: List<PrincipleEntity>
) {

    companion object {

        val EMPTY = FeedbackEntity(
            code = "",
            message = "",
            summarize = "",
            summarizeOfMarket = "",
            principles = emptyList()
        )
    }
}
