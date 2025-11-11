package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.retrofit.model.FeedbackResponse
import com.depromeet.team5.core.retrofit.model.MyPrincipleGroupInfoResponse
import com.depromeet.team5.core.retrofit.model.MyPrincipleGroupsInfoResponse
import com.depromeet.team5.core.retrofit.model.RetrospectionListResponse
import com.depromeet.team5.core.retrofit.model.SearchResponse
import com.depromeet.team5.core.retrofit.model.SystemPrincipleResponse
import com.depromeet.team5.core.retrofit.model.UserStatsResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HedgeApi {

    @GET("api/v1/stock/search")
    suspend fun search(@Query("query") query: String): SearchResponse

    @POST("api/v1/reports/{retrospectionId}/feedback")
    suspend fun createFeedback(
        @Path("retrospectionId") retrospectionId: Int
    ): FeedbackResponse

    @GET("api/v1/principle-groups")
    suspend fun getPrincipleGroups(
        @Query("type")
        orderType: String
    ): MyPrincipleGroupsInfoResponse

    @GET("api/v1/principle-groups/{groupId}")
    suspend fun getPrincipleGroup(
        @Path("groupId")
        groupId: Int
    ): MyPrincipleGroupInfoResponse

    @DELETE("api/v1/principle-groups/{groupId}")
    suspend fun deletePrincipleGroup(
        @Path("groupId")
        groupId: Int
    )

    @DELETE("api/v1/investment-principles/{principleId}")
    suspend fun deletePrinciple(
        @Path("principleId")
        principleId: Int
    )

    @POST("api/v1/principle-groups")
    suspend fun createPrincipleGroup(
        @Body body: RequestBody
    ): MyPrincipleGroupInfoResponse

    @GET("api/v1/reports")
    suspend fun userStats(): UserStatsResponse

    @GET("api/v1/retrospections")
    suspend fun retrospectionList(): RetrospectionListResponse

    @GET("api/v1/principle-groups/systems")
    suspend fun systemPrincipleList(): SystemPrincipleResponse

}
