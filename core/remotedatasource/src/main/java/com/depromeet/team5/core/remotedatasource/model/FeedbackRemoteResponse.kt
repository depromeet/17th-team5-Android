package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.remotedatasource.mapper.DataMapper


data class FeedbackRemoteResponse(
    val code: String,
    val message: String,
    val summarize: String,
    val summarizeOfMarket: String,
    val principles: List<PrincipleRemoteResponse>
) : DataMapper<FeedbackData> {

    override fun toData(): FeedbackData = FeedbackData(
        code = code,
        message = message,
        summarize = summarize,
        summarizeOfMarket = summarizeOfMarket,
        principles = principles.map { it.toData() }
    )

    companion object {

        val EMPTY = FeedbackRemoteResponse(
            code = "",
            message = "",
            summarize = "",
            summarizeOfMarket = "",
            principles = emptyList()
        )
    }
}
