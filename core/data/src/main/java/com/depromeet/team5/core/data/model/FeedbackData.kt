package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.Feedback


data class FeedbackData(
    val code: String,
    val message: String,
    val summarize: String,
    val summarizeOfMarket: String,
    val principles: List<PrincipleData>
) : DataMapper<Feedback> {

    override fun toDomain(): Feedback = Feedback(
        code = code,
        message = message,
        summarize = summarize,
        summarizeOfMarket = summarizeOfMarket,
        principles = principles.map { it.toDomain() }
    )

}
