package com.depromeet.team5.core.remotedatasource.apisource

import android.net.Uri
import com.depromeet.team5.core.remotedatasource.model.BaseRemoteData
import com.depromeet.team5.core.remotedatasource.model.MemoRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData


interface RetrospectionApiSource {

    suspend fun createRetrospection(request: CreateRetrospectionRequestRemoteData): BaseRemoteData<RetrospectionRemoteData>

    suspend fun getRetrospection(retrospectionId: Int): BaseRemoteData<RetrospectionRemoteData>

    suspend fun deleteRetrospection(retrospectionId: Int): BaseRemoteData<String>

    suspend fun uploadImageUri(
        domain: String,
        uri: Uri,
        fileName: String? = null
    ): Int

    suspend fun createMemo(
        retrospectionId: Int,
        content: String,
    ): BaseRemoteData<MemoRemoteData>

    suspend fun updateMemo(
        retrospectionId: Int,
        memoId: Int,
        content: String,
    ): BaseRemoteData<MemoRemoteData>

    suspend fun deleteMemo(
        retrospectionId: Int,
        memoId: Int,
    ): BaseRemoteData<String>
}
