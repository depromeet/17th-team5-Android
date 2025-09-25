package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteResponse
import com.depromeet.team5.core.retrofit.mapper.ResponseMapper


data class FeedbackDto(
    val code: String,
    val data: FeedbackDataDto,
    val message: String
) : ResponseMapper<FeedbackRemoteResponse> {
    override fun toRemoteResponse(): FeedbackRemoteResponse = FeedbackRemoteResponse(
        code = code,
        message = message,
        feedback = data.feedback
    )
}

data class FeedbackDataDto(
    val feedback: String
)
