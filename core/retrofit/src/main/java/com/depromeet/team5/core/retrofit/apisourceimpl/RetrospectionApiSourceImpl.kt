package com.depromeet.team5.core.retrofit.apisourceimpl

import com.depromeet.team5.core.remotedatasource.apisource.RetrospectionApiSource
import com.depromeet.team5.core.remotedatasource.model.BaseRemoteData
import com.depromeet.team5.core.remotedatasource.model.MemoRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.retrofit.api.RetrospectionApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrospectionApiSourceImpl @Inject constructor(
    private val retrospectionApi: RetrospectionApi,
) : RetrospectionApiSource {

    override suspend fun getRetrospection(retrospectionId: Int): BaseRemoteData<RetrospectionRemoteData> =
        retrospectionApi
            .getRetrospection(retrospectionId)
            .toBaseRemoteData()

    override suspend fun deleteRetrospection(retrospectionId: Int): BaseRemoteData<String> =
        retrospectionApi
            .deleteRetrospection(retrospectionId)
            .toBaseRemoteData()

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

}
