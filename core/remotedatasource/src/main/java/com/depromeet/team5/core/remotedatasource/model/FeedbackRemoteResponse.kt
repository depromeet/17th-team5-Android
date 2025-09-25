package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.remotedatasource.mapper.DataMapper


data class FeedbackRemoteResponse(
    val code: String,
    val message: String,
    val feedback: String
) : DataMapper<FeedbackData> {

    override fun toData(): FeedbackData = FeedbackData(
        code = code,
        message = message,
        feedback = feedback
    )
}
