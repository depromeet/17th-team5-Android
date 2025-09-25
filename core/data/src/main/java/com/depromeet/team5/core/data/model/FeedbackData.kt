package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DomainMapper
import com.depromeet.team5.core.domain.model.FeedbackEntity


data class FeedbackData(
    val code: String,
    val message: String,
    val feedback: String
) : DomainMapper<FeedbackEntity> {

    override fun toDomain(): FeedbackEntity = FeedbackEntity(
        code = code,
        message = message,
        feedback = feedback
    )

}
