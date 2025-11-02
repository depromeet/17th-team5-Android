package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.retrofit.model.AnalysisResponse
import com.depromeet.team5.core.retrofit.model.FeedbackResponse
import com.depromeet.team5.core.retrofit.model.MyPrincipleResponse
import com.depromeet.team5.core.retrofit.model.RetrospectionResponse
import com.depromeet.team5.core.retrofit.model.SearchResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface HedgeApi {

    @GET("api/v1/stock/search")
    suspend fun search(@Query("query") query: String): SearchResponse

    @POST("api/v1/retrospections")
    suspend fun createRetrospection(@Body body: RequestBody): RetrospectionResponse

    @POST("api/v1/reports/{retrospectionId}/feedback")
    suspend fun createFeedback(
        @Path("retrospectionId") retrospectionId: Int
    ): FeedbackResponse

    @GET("api/analysis/v1")
    suspend fun createAnalysis(@QueryMap query: Map<String, String>): AnalysisResponse

    @GET("api/v1/principle-groups")
    suspend fun getPrinciples(
        @Query("type")
        orderType: String
    ): MyPrincipleResponse

}
