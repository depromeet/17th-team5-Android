package com.depromeet.team5.core.retrofit.apisourceimpl

import android.content.Context
import android.net.Uri
import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.MyPrincipleGroupsRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListRemoteData
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.UserStatsRemoteData
import com.depromeet.team5.core.retrofit.api.HedgeApi
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class HedgeApiSourceImpl @Inject constructor(
    private val hedgeApi: HedgeApi,
    @ApplicationContext private val context: Context
) : HedgeApiSource {

    override suspend fun search(query: String): SearchRemoteData =
        hedgeApi.search(query = query).toRemoteData()

    override suspend fun createRetrospection(
        request: CreateRetrospectionRequestRemoteData
    ): RetrospectionRemoteData = hedgeApi.createRetrospection(request).toRemoteData()

    override suspend fun createFeedback(retrospectionId: Int): FeedbackRemoteData = hedgeApi
        .createFeedback(retrospectionId = retrospectionId)
        .toRemoteData()

    override suspend fun getPrincipleGroups(orderType: String): MyPrincipleGroupsRemoteData =
        hedgeApi.getPrincipleGroups(orderType = orderType).toRemoteData()

    override suspend fun uploadImageUri(
        domain: String,
        uri: Uri,
        fileName: String?
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

}
