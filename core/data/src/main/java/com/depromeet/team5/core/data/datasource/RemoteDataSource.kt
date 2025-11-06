package com.depromeet.team5.core.data.datasource

import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.MyPrincipleGroupsData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.data.model.SearchData
import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData


interface RemoteDataSource {

    suspend fun search(query: String): SearchData

    suspend fun createRetrospection(request: CreateRetrospectionRequestData): RetrospectionData

    suspend fun createFeedback(retrospectionId: Int): FeedbackData

    suspend fun getPrinciples(orderType: String): MyPrincipleGroupsData
}
