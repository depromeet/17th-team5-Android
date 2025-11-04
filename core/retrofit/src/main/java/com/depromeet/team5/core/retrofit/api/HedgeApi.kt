package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.retrofit.model.BaseResponse
import com.depromeet.team5.core.retrofit.model.FeedbackResponse
import com.depromeet.team5.core.retrofit.model.ImageUploadResponse
import com.depromeet.team5.core.retrofit.model.MyPrincipleGroupResponse
import com.depromeet.team5.core.retrofit.model.MyPrincipleGroupsResponse
import com.depromeet.team5.core.retrofit.model.MyPrincipleResponse
import com.depromeet.team5.core.retrofit.model.RetrospectionListResponse
import com.depromeet.team5.core.retrofit.model.RetrospectionResponse
import com.depromeet.team5.core.retrofit.model.SearchResponse
import com.depromeet.team5.core.retrofit.model.SystemPrincipleResponse
import com.depromeet.team5.core.retrofit.model.UserStatsResponse
import okhttp3.RequestBody
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface HedgeApi {

    @GET("api/v1/stock/search")
    suspend fun search(@Query("query") query: String): SearchResponse

    @POST("api/v1/retrospections")
    suspend fun createRetrospection(@Body body: CreateRetrospectionRequestRemoteData): RetrospectionResponse

    @POST("api/v1/reports/{retrospectionId}/feedback")
    suspend fun createFeedback(
        @Path("retrospectionId") retrospectionId: Int
    ): FeedbackResponse

    @GET("api/v1/principle-groups")
    suspend fun getPrincipleGroups(
        @Query("type")
        orderType: String
    ): MyPrincipleGroupsResponse

    @GET("api/v1/principle-groups/{groupId}")
    suspend fun getPrincipleGroup(
        @Path("groupId")
        groupId: Int
    ): MyPrincipleGroupResponse

    @DELETE("api/v1/principle-groups/{groupId}")
    suspend fun deletePrincipleGroup(
        @Path("groupId")
        groupId: Int
    )

    @Multipart
    @POST("api/v1/{domain}/images/upload")
    suspend fun imageUpload(
        @Path("domain") domain: String,
        @Part file: MultipartBody.Part,
    ): BaseResponse<ImageUploadResponse>

    @GET("api/v1/reports")
    suspend fun userStats(): UserStatsResponse

    @GET("api/v1/retrospections")
    suspend fun retrospectionList(): RetrospectionListResponse

    @GET("api/v1/principle-groups/systems")
    suspend fun systemPrincipleList(): SystemPrincipleResponse

}
