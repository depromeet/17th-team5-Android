package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.retrofit.model.RetrospectionDto
import retrofit2.http.Body
import retrofit2.http.POST


interface HedgeApi {

    @POST("api/v1/retrospections")
    suspend fun createRetrospection(@Body body: Map<String, Any>): RetrospectionDto
}
