package com.depromeet.team5.core.model

import androidx.compose.runtime.Stable
import com.depromeet.team5.core.domain.model.FeedbackEntity
import com.depromeet.team5.core.model.mapper.DomainMapper


@Stable
data class Feedback(
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
