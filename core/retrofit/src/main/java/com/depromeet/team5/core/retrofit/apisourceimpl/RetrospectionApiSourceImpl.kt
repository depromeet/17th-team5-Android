package com.depromeet.team5.core.retrofit.apisourceimpl

import android.content.Context
import android.net.Uri
import com.depromeet.team5.core.remotedatasource.apisource.RetrospectionApiSource
import com.depromeet.team5.core.remotedatasource.model.BaseRemoteData
import com.depromeet.team5.core.remotedatasource.model.MemoRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.retrofit.api.RetrospectionApi
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrospectionApiSourceImpl @Inject constructor(
    private val retrospectionApi: RetrospectionApi,
    @ApplicationContext private val context: Context,
) : RetrospectionApiSource {

    override suspend fun createRetrospection(
        request: CreateRetrospectionRequestRemoteData
    ): BaseRemoteData<RetrospectionRemoteData> = retrospectionApi.createRetrospection(request).toBaseRemoteData()

    override suspend fun getRetrospection(retrospectionId: Int): BaseRemoteData<RetrospectionRemoteData> =
        retrospectionApi
            .getRetrospection(retrospectionId)
            .toBaseRemoteData()

    override suspend fun deleteRetrospection(retrospectionId: Int): BaseRemoteData<String> =
        retrospectionApi
            .deleteRetrospection(retrospectionId)
            .toBaseRemoteData()

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
        return retrospectionApi.imageUpload(domain, part).data?.imageId ?: -1
    }

    override suspend fun createMemo(retrospectionId: Int, content: String): BaseRemoteData<MemoRemoteData> =
        retrospectionApi
            .createMemo(
                retrospectionId = retrospectionId,
                body = mapOf("content" to content),
            )
            .toBaseRemoteData()

    override suspend fun updateMemo(retrospectionId: Int, memoId: Int, content: String): BaseRemoteData<MemoRemoteData> =
        retrospectionApi
            .updateMemo(
                retrospectionId = retrospectionId,
                memoId = memoId,
                body = mapOf("content" to content),
            )
            .toBaseRemoteData()

    override suspend fun deleteMemo(retrospectionId: Int, memoId: Int): BaseRemoteData<String> =
        retrospectionApi
            .deleteMemo(
                retrospectionId = retrospectionId,
                memoId = memoId,
            )
            .toBaseRemoteData()

    private fun guessFileName(mime: String): String {
        val ext = when {
            mime.contains("jpeg") -> "jpg"
            mime.contains("png") -> "png"
            mime.contains("webp") -> "webp"
            else -> "bin"
        }
        return "image_${System.currentTimeMillis()}.$ext"
    }
}
