package com.depromeet.team5.core.remotedatasource.apisource

import com.depromeet.team5.core.remotedatasource.model.BaseRemoteData
import com.depromeet.team5.core.remotedatasource.model.MemoRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData


interface RetrospectionApiSource {

    suspend fun getRetrospection(retrospectionId: Int): BaseRemoteData<RetrospectionRemoteData>

    suspend fun deleteRetrospection(retrospectionId: Int): BaseRemoteData<String>

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
