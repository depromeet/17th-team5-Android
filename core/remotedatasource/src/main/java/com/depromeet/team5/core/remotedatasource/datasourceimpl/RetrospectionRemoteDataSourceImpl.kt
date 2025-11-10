package com.depromeet.team5.core.remotedatasource.datasourceimpl

import com.depromeet.team5.core.data.datasource.RetrospectionRemoteDataSource
import com.depromeet.team5.core.data.model.BaseData
import com.depromeet.team5.core.data.model.MemoData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.remotedatasource.apisource.RetrospectionApiSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RetrospectionRemoteDataSourceImpl @Inject constructor(
    private val retrospectionApiSource: RetrospectionApiSource,
) : RetrospectionRemoteDataSource {

    override suspend fun getRetrospection(retrospectionId: Int): BaseData<RetrospectionData> =
        retrospectionApiSource.getRetrospection(retrospectionId).toBaseData()

    override suspend fun deleteRetrospection(retrospectionId: Int): BaseData<String> =
        retrospectionApiSource.deleteRetrospection(retrospectionId).toBaseData()

    override suspend fun createMemo(retrospectionId: Int, content: String): BaseData<MemoData> =
        retrospectionApiSource.createMemo(retrospectionId, content).toBaseData()

    override suspend fun updateMemo(retrospectionId: Int, memoId: Int, content: String): BaseData<MemoData> =
        retrospectionApiSource.updateMemo(retrospectionId, memoId, content).toBaseData()

    override suspend fun deleteMemo(retrospectionId: Int, memoId: Int): BaseData<String> =
        retrospectionApiSource.deleteMemo(retrospectionId, memoId).toBaseData()
}
