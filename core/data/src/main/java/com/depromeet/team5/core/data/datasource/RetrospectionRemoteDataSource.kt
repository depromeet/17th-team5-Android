package com.depromeet.team5.core.data.datasource

import com.depromeet.team5.core.data.model.BaseData
import com.depromeet.team5.core.data.model.MemoData
import com.depromeet.team5.core.data.model.RetrospectionData


interface RetrospectionRemoteDataSource {

    suspend fun getRetrospection(retrospectionId: Int): BaseData<RetrospectionData>

    suspend fun deleteRetrospection(retrospectionId: Int): BaseData<String>

    suspend fun createMemo(
        retrospectionId: Int,
        content: String,
    ): BaseData<MemoData>

    suspend fun updateMemo(
        retrospectionId: Int,
        memoId: Int,
        content: String,
    ): BaseData<MemoData>

    suspend fun deleteMemo(
        retrospectionId: Int,
        memoId: Int,
    ): BaseData<String>
}
