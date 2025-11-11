package com.depromeet.team5.core.retrofit.apisourceimpl

import android.content.Context
import android.net.Uri
import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData
import com.depromeet.team5.core.remotedatasource.model.SocialLoginRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.UserStatsRemoteData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.remotedatasource.request.SocialLoginRequestRemoteData
import com.depromeet.team5.core.retrofit.api.HedgeApi
import com.depromeet.team5.core.retrofit.model.SocialLoginFailureResponse
import com.depromeet.team5.core.retrofit.model.SocialLoginSuccessResponse
import com.depromeet.team5.core.retrofit.toRequestBody
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class HedgeApiSourceImpl @Inject constructor(
    private val hedgeApi: HedgeApi,
    @ApplicationContext private val context: Context,
    private val json: Json,
) : HedgeApiSource {

    override suspend fun search(query: String): SearchRemoteData =
        hedgeApi.search(query = query).toRemoteData()

    override suspend fun createRetrospection(
        request: CreateRetrospectionRequestRemoteData,
    ): RetrospectionRemoteData = hedgeApi.createRetrospection(request).toRemoteData()

    override suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData = hedgeApi
        .createFeedback(retrospectionId = retrospectionId)
        .toRemoteData()

    override suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsInfoRemoteData =
        hedgeApi.getPrincipleGroups(orderType = orderType).toRemoteData()

    override suspend fun getPrincipleGroup(groupId: Int): MyPrincipleGroupRemoteData =
        hedgeApi.getPrincipleGroup(groupId).toRemoteData()

    override suspend fun deletePrincipleGroup(groupId: Int) =
        hedgeApi.deletePrincipleGroup(groupId)

    override suspend fun deletePrinciple(principleId: Int) =
        hedgeApi.deletePrinciple(principleId)

    override suspend fun createPrincipleGroup(body: Map<String, Any?>): MyPrincipleGroupRemoteData =
        hedgeApi.createPrincipleGroup(body.toRequestBody()).toRemoteData()

    override suspend fun uploadImageUri(
        domain: String,
        uri: Uri,
        fileName: String?,
    ): Int {
        val mime = context.contentResolver.getType(uri) ?: "image/*"
        val name = fileName ?: guessFileName(mime)

        val bytes = context.contentResolver.openInputStream(uri)!!.use { it.readBytes() }
        val reqBody = bytes.toRequestBody(mime.toMediaType())
        val part = MultipartBody.Part.createFormData("file", name, reqBody)
        return hedgeApi.imageUpload(domain, part).data?.imageId ?: -1
    }

    private fun guessFileName(mime: String): String {
        val ext = when {
            mime.contains("jpeg") -> "jpg"
            mime.contains("png") -> "png"
            mime.contains("webp") -> "webp"
            else -> "bin"
        }
        return "image_${System.currentTimeMillis()}.$ext"
    }

    override suspend fun userStats(): UserStatsRemoteData =
        hedgeApi
            .userStats()
            .toRemoteData()

    override suspend fun retrospectionList(): RetrospectionListRemoteData =
        hedgeApi
            .retrospectionList()
            .toRemoteData()

    override suspend fun systemPrincipleList(): SystemPrincipleRemoteData =
        hedgeApi
            .systemPrincipleList()
            .toRemoteData()

    override suspend fun socialLogin(body: SocialLoginRequestRemoteData): SocialLoginRemoteData {
        val resp: Response<SocialLoginSuccessResponse> = hedgeApi.socialLogin(body)
        return if (resp.isSuccessful) {
            val success = resp.body()
                ?: return SocialLoginRemoteData.Failure(
                    code = "EMPTY_BODY",
                    message = "Empty response body",
                    data = null
                )
            success.toRemoteData()
        } else {
            val errorText = resp.errorBody()?.string().orEmpty()
            val failure = runCatching {
                json.decodeFromString(SocialLoginFailureResponse.serializer(), errorText)
            }.getOrElse {
                SocialLoginFailureResponse(
                    code = resp.code().toString(),
                    message = "HTTP ${resp.code()} ${resp.message()}",
                    data = null
                )
            }
            failure.toRemoteData()
        }
    }
}
