package com.depromeet.team5.core.data.datasource

import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.RetrospectionData


interface RemoteDataSource {

    suspend fun createRetrospection(body: Map<String, Any?>): RetrospectionData

    suspend fun createFeedback(
        retrospectionId: Int,
        body: Map<String, Any?>
    ): FeedbackData
}
