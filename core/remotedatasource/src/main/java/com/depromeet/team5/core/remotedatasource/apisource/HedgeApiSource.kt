package com.depromeet.team5.core.remotedatasource.apisource

import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData


interface HedgeApiSource {

    suspend fun search(query: String): SearchRemoteData

    suspend fun createRetrospection(body: Map<String, Any?>): RetrospectionRemoteData

    suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData

    suspend fun getPrinciples(orderType: String): MyPrincipleRemoteData
}
