package com.depromeet.team5.core.data.repositoryimpl

import com.depromeet.team5.core.data.datasource.RetrospectionRemoteDataSource
import com.depromeet.team5.core.domain.model.BaseDomain
import com.depromeet.team5.core.domain.model.Memo
import com.depromeet.team5.core.domain.model.Retrospection
import com.depromeet.team5.core.domain.repository.RetrospectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


internal class RetrospectionRepositoryImpl @Inject constructor(
    private val retrospectionRemoteDataSource: RetrospectionRemoteDataSource,
) : RetrospectionRepository {

    override fun getRetrospection(retrospectionId: Int): Flow<BaseDomain<Retrospection>> = flow {
        emit(retrospectionRemoteDataSource.getRetrospection(retrospectionId).toBaseDomain())
    }

    override fun deleteRetrospection(retrospectionId: Int): Flow<BaseDomain<String>> = flow {
        emit(retrospectionRemoteDataSource.deleteRetrospection(retrospectionId).toBaseDomain())
    }

    override fun createMemo(retrospectionId: Int, content: String): Flow<BaseDomain<Memo>> = flow {
        emit(retrospectionRemoteDataSource.createMemo(retrospectionId, content).toBaseDomain())
    }

    override fun updateMemo(retrospectionId: Int, memoId: Int, content: String): Flow<BaseDomain<Memo>> = flow {
        emit(retrospectionRemoteDataSource.updateMemo(retrospectionId, memoId, content).toBaseDomain())
    }

    override fun deleteMemo(retrospectionId: Int, memoId: Int): Flow<BaseDomain<String>> = flow {
        emit(retrospectionRemoteDataSource.deleteMemo(retrospectionId, memoId).toBaseDomain())
    }
}
