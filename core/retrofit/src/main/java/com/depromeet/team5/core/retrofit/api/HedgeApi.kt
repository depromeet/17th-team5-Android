package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.remotedatasource.request.SocialLoginRequestRemoteData
import com.depromeet.team5.core.retrofit.model.FeedbackResponse
import com.depromeet.team5.core.retrofit.model.MyPrincipleGroupInfoResponse
import com.depromeet.team5.core.retrofit.model.MyPrincipleGroupsInfoResponse
import com.depromeet.team5.core.retrofit.model.MyPrincipleInfoResponse
import com.depromeet.team5.core.retrofit.model.RetrospectionListResponse
import com.depromeet.team5.core.retrofit.model.StockSliceResponse
import com.depromeet.team5.core.retrofit.model.SocialLoginSuccessResponse
import com.depromeet.team5.core.retrofit.model.SystemPrincipleResponse
import com.depromeet.team5.core.retrofit.model.UserStatsResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HedgeApi {

    @GET("api/v1/stock/slice")
    suspend fun getStockSlice(
        @Query("companyName") companyName: String,
        @Query("nextCursor") nextCursor: String? = null,
        @Query("size") size: Int? = null
    ): StockSliceResponse

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

    @POST("api/v1/principle-groups")
    suspend fun createPrincipleGroup(
        @Body body: RequestBody
    ): MyPrincipleGroupInfoResponse

    @DELETE("api/v1/investment-principles/{principleId}")
    suspend fun deletePrinciple(
        @Path("principleId")
        principleId: Int
    )

    @PATCH("api/v1/investment-principles/{principleId}")
    suspend fun modifyPrinciple(
        @Path("principleId") principleId: Int,
        @Body body: RequestBody
    ): MyPrincipleInfoResponse

    @PATCH("api/v1/principle-groups/{groupId}")
    suspend fun modifyPrincipleGroup(
        @Path("groupId") groupId: Int,
        @Body body: RequestBody
    ): MyPrincipleGroupInfoResponse

    @POST("api/v1/investment-principles")
    suspend fun addPrinciple(
        @Body body: RequestBody
    ): MyPrincipleInfoResponse

    @GET("api/v1/reports")
    suspend fun userStats(): UserStatsResponse

    @GET("api/v1/retrospections")
    suspend fun retrospectionList(): RetrospectionListResponse

    @GET("api/v1/principle-groups/systems")
    suspend fun systemPrincipleList(): SystemPrincipleResponse

    @POST("api/v1/auth/social-login")
    suspend fun socialLogin(
        @Body body: SocialLoginRequestRemoteData
    ): Response<SocialLoginSuccessResponse>

    @GET("api/v1/reports/{retrospectionId}/feedback")
    suspend fun getFeedback(
        @Path("retrospectionId") retrospectionId: Int
    ): FeedbackResponse
}
