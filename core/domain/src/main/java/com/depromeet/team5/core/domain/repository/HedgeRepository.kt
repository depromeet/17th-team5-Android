package com.depromeet.team5.core.domain.repository

import com.depromeet.team5.core.domain.model.FeedbackEntity
import com.depromeet.team5.core.domain.model.RetrospectionEntity
import com.depromeet.team5.core.domain.model.SearchEntity
import kotlinx.coroutines.flow.Flow


interface HedgeRepository {

    fun search(query: String): Flow<SearchEntity>

    fun createRetrospection(body: Map<String, Any?>): Flow<RetrospectionEntity>

    fun createFeedback(
        retrospectionId: Int
    ): Flow<FeedbackEntity>
}
