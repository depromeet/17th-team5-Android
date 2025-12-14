package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.retrofit.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginApi {

    @POST("api/v1/auth/refresh")
    suspend fun refreshAccessToken(
        @Body body: Map<String, Any?>
    ): TokenResponse
}
