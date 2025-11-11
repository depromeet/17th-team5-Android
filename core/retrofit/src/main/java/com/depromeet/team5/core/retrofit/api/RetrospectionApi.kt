package com.depromeet.team5.core.retrofit.api

import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.retrofit.model.BaseResponse
import com.depromeet.team5.core.retrofit.model.ImageUploadResponse
import com.depromeet.team5.core.retrofit.model.MemoResponse
import com.depromeet.team5.core.retrofit.model.RetrospectionResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface RetrospectionApi {

    @POST(PATH)
    suspend fun createRetrospection(@Body body: CreateRetrospectionRequestRemoteData): BaseResponse<RetrospectionResponse>

    @GET("$PATH/{retrospectionId}")
    suspend fun getRetrospection(
        @Path("retrospectionId") retrospectionId: Int
    ): BaseResponse<RetrospectionResponse>

    @DELETE("$PATH/{retrospectionId}")
    suspend fun deleteRetrospection(
        @Path("retrospectionId") retrospectionId: Int,
    ): BaseResponse<String>

    @Multipart
    @POST("api/v1/{domain}/images/upload")
    suspend fun imageUpload(
        @Path("domain") domain: String,
        @Part file: MultipartBody.Part,
    ): BaseResponse<ImageUploadResponse>

    @POST("$PATH/{retrospectionId}/memos")
    suspend fun createMemo(
        @Path("retrospectionId") retrospectionId: Int,
        @Body body: Map<String, String>
    ): BaseResponse<MemoResponse>

    @PUT("$PATH/{retrospectionId}/memos/{memoId}")
    suspend fun updateMemo(
        @Path("retrospectionId") retrospectionId: Int,
        @Path("memoId") memoId: Int,
        @Body body: Map<String, String>
    ): BaseResponse<MemoResponse>

    @DELETE("$PATH/{retrospectionId}/memos/{memoId}")
    suspend fun deleteMemo(
        @Path("retrospectionId") retrospectionId: Int,
        @Path("memoId") memoId: Int
    ): BaseResponse<String>


    companion object {
        private const val PATH = "api/v1/retrospections"
    }
}
