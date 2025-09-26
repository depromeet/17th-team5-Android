package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.FeedbackEntity


data class FeedbackData(
    val code: String,
    val message: String,
    val summarize: String,
    val summarizeOfMarket: String,
    val principles: List<PrincipleData>
) : DataMapper<FeedbackEntity> {

    override fun toDomain(): FeedbackEntity = FeedbackEntity(
        code = code,
        message = message,
        summarize = summarize,
        summarizeOfMarket = summarizeOfMarket,
        principles = principles.map { it.toDomain() }
    )

}
