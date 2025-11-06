package com.depromeet.team5.core.remotedatasource.apisource

import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData


interface HedgeApiSource {

    suspend fun search(query: String): SearchRemoteData

    suspend fun createRetrospection(request: CreateRetrospectionRequestRemoteData): RetrospectionRemoteData

    suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData

    suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsRemoteData
}
