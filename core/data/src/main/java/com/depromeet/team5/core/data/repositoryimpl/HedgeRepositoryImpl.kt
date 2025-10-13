package com.depromeet.team5.core.data.repositoryimpl

import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.domain.model.Analysis
import com.depromeet.team5.core.domain.model.Feedback
import com.depromeet.team5.core.domain.model.Retrospection
import com.depromeet.team5.core.domain.model.Search
import com.depromeet.team5.core.domain.repository.HedgeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


internal class HedgeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : HedgeRepository {

    override fun search(query: String): Flow<Search> = flow {
        emit(remoteDataSource.search(query).toDomain())
    }

    override fun createRetrospection(body: Map<String, Any?>): Flow<Retrospection> = flow {
        emit(remoteDataSource.createRetrospection(body).toDomain())
    }

    override fun createFeedback(
        retrospectionId: Int
    ): Flow<Feedback> = flow {
        emit(remoteDataSource.createFeedback(retrospectionId).toDomain())
    }

    override fun createAnalysis(body: Map<String, Any?>): Flow<Analysis> {
        return flow {
            emit(remoteDataSource.createAnalysis(body).toDomain())
        }
    }
}
