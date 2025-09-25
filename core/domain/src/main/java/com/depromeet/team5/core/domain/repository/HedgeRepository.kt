package com.depromeet.team5.core.domain.repository

import com.depromeet.team5.core.domain.model.FeedbackEntity
import com.depromeet.team5.core.domain.model.RetrospectionEntity
import kotlinx.coroutines.flow.Flow


interface HedgeRepository {

    fun createRetrospection(body: Map<String, Any?>): Flow<RetrospectionEntity>

    fun createFeedback(
        retrospectionId: Int,
        body: Map<String, Any?>
    ): Flow<FeedbackEntity>
}
