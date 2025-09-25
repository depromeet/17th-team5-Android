package com.depromeet.team5.core.retrofit.api

import retrofit2.http.Body
import retrofit2.http.POST


interface HedgeApi {

    @POST("api/v1/retrospections")
    fun createRetrospection(@Body body: Map<String, Any>)
}
