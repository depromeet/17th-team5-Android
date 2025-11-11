package com.depromeet.team5.core.domain.repository

import com.depromeet.team5.core.domain.model.BaseDomain
import com.depromeet.team5.core.domain.model.Memo
import com.depromeet.team5.core.domain.model.Retrospection
import kotlinx.coroutines.flow.Flow


interface RetrospectionRepository {

    fun getRetrospection(retrospectionId: Int): Flow<BaseDomain<Retrospection>>

    fun deleteRetrospection(retrospectionId: Int): Flow<BaseDomain<String>>

    fun createMemo(
        retrospectionId: Int,
        content: String,
    ): Flow<BaseDomain<Memo>>

    fun updateMemo(
        retrospectionId: Int,
        memoId: Int,
        content: String,
    ): Flow<BaseDomain<Memo>>

    fun deleteMemo(
        retrospectionId: Int,
        memoId: Int,
    ): Flow<BaseDomain<String>>
}
