package com.depromeet.team5.core.remotedatasource.apisource

import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData


interface HedgeApiSource {

    suspend fun createRetrospection(body: Map<String, Any?>): RetrospectionRemoteData

    suspend fun createFeedback(
        retrospectionId: Int,
        body: Map<String, Any?>
    ): String
}
