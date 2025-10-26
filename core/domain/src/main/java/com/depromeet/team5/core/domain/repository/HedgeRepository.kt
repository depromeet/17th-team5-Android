package com.depromeet.team5.core.domain.repository

import com.depromeet.team5.core.domain.model.Analysis
import com.depromeet.team5.core.domain.model.Feedback
import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.model.Retrospection
import com.depromeet.team5.core.domain.model.Search
import kotlinx.coroutines.flow.Flow


interface HedgeRepository {

    fun search(query: String): Flow<Search>

    fun createRetrospection(body: Map<String, Any?>): Flow<Retrospection>

    fun createFeedback(
        retrospectionId: Int
    ): Flow<Feedback>

    fun createAnalysis(body: Map<String, Any?>): Flow<Analysis>

    fun getPrinciples(): Flow<Map<Int, List<MyPrinciple>>>
}
