package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper


data class FeedbackRemoteData(
    val code: String,
    val message: String,
    val summarize: String,
    val summarizeOfMarket: String,
    val principles: List<PrincipleRemoteData>
) : RemoteDataMapper<FeedbackData> {

    override fun toData(): FeedbackData = FeedbackData(
        code = code,
        message = message,
        summarize = summarize,
        summarizeOfMarket = summarizeOfMarket,
        principles = principles.map { it.toData() }
    )

    companion object {

        val EMPTY = FeedbackRemoteData(
            code = "",
            message = "",
            summarize = "",
            summarizeOfMarket = "",
            principles = emptyList()
        )
    }
}
