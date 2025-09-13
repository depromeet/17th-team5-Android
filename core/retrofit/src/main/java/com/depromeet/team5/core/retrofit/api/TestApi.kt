package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.retrofit.model.TestApiResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface TestApi {
    @GET("test")
    suspend fun getTest(): Response<TestApiResponse>
}