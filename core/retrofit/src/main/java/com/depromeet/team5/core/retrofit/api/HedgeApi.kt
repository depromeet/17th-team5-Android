package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.retrofit.model.FeedbackDto
import com.depromeet.team5.core.retrofit.model.RetrospectionResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST


interface HedgeApi {

    @POST("api/v1/retrospections")
    suspend fun createRetrospection(@Body body: RequestBody): RetrospectionResponse

    @POST("api/v1/reports/{retrospectionId}/feedback")
    suspend fun createFeedback(
        @Path("retrospectionId") retrospectionId: Int,
        @Body body: RequestBody
    ): FeedbackDto
}
