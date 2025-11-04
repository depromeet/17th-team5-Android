package com.depromeet.team5.core.data.datasource

import com.depromeet.team5.core.data.model.AnalysisData
import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.MyPrincipleGroupsData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.data.model.SearchData


interface RemoteDataSource {

    suspend fun search(query: String): SearchData

    suspend fun createRetrospection(body: Map<String, Any?>): RetrospectionData

    suspend fun createFeedback(retrospectionId: Int): FeedbackData

    suspend fun createAnalysis(body: Map<String, Any?>): AnalysisData

    suspend fun getPrinciples(orderType: String): MyPrincipleGroupsData
}
