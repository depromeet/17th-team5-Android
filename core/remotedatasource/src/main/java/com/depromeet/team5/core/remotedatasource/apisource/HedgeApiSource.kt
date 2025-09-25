package com.depromeet.team5.core.remotedatasource.apisource

import com.depromeet.team5.core.remotedatasource.model.RetrospectionResponse


interface HedgeApiSource {

    suspend fun createRetrospection(body: Map<String, Any?>): RetrospectionResponse
}
