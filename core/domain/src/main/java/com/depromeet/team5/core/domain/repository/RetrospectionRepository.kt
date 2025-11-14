package com.depromeet.team5.core.domain.repository

import com.depromeet.team5.core.domain.model.Article
import com.depromeet.team5.core.domain.model.BaseDomain
import com.depromeet.team5.core.domain.model.Memo
import com.depromeet.team5.core.domain.model.Retrospection
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import kotlinx.coroutines.flow.Flow


interface RetrospectionRepository {

    fun createRetrospection(request: CreateRetrospectionRequest): Flow<BaseDomain<Retrospection>>

    fun getRetrospection(retrospectionId: Int): Flow<BaseDomain<Retrospection>>

    fun deleteRetrospection(retrospectionId: Int): Flow<BaseDomain<String>>

    suspend fun uploadImageUri(
        domain: String,
        uri: String,
        fileName: String? = null
    ): Int

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

    suspend fun parseArticle(
        url: String,
    ): Article
}
