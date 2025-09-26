package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.retrofit.model.RetrospectionResponse
import com.depromeet.team5.core.retrofit.model.SearchResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface HedgeApi {

    @GET("api/v1/stock/search")
    suspend fun search(@Query("query") query: String): SearchResponse

    @POST("api/v1/retrospections")
    suspend fun createRetrospection(@Body body: RequestBody): RetrospectionResponse

    @POST("api/v1/reports/{retrospectionId}/feedback")
    suspend fun createFeedback(
        @Path("retrospectionId") retrospectionId: Int
    ): ResponseBody
}
