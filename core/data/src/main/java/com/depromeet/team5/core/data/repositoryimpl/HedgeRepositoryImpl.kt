package com.depromeet.team5.core.data.repositoryimpl

import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.domain.model.AnalysisEntity
import com.depromeet.team5.core.domain.model.FeedbackEntity
import com.depromeet.team5.core.domain.model.RetrospectionEntity
import com.depromeet.team5.core.domain.model.SearchEntity
import com.depromeet.team5.core.domain.repository.HedgeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


internal class HedgeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : HedgeRepository {

    override fun search(query: String): Flow<SearchEntity> = flow {
        emit(remoteDataSource.search(query).toDomain())
    }

    override fun createRetrospection(body: Map<String, Any?>): Flow<RetrospectionEntity> = flow {
        emit(remoteDataSource.createRetrospection(body).toDomain())
    }

    override fun createFeedback(
        retrospectionId: Int
    ): Flow<FeedbackEntity> = flow {
        emit(remoteDataSource.createFeedback(retrospectionId).toDomain())
    }

    override fun createAnalysis(body: Map<String, Any?>): Flow<AnalysisEntity> {
        return flow {
            emit(remoteDataSource.createAnalysis(body).toDomain())
        }
    }
}
