package com.depromeet.team5.core.data.repositoryimpl

import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.mapper.toData
import com.depromeet.team5.core.domain.model.Feedback
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.Retrospection
import com.depromeet.team5.core.domain.model.Search
import com.depromeet.team5.core.domain.repository.HedgeRepository
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


internal class HedgeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : HedgeRepository {

    override fun search(query: String): Flow<Search> = flow {
        emit(remoteDataSource.search(query).toDomain())
    }

    override suspend fun createRetrospection(request: CreateRetrospectionRequest): Retrospection =
        remoteDataSource.createRetrospection(request.toData()).toDomain()

    override fun createFeedback(
        retrospectionId: Int
    ): Flow<Feedback> = flow {
        emit(remoteDataSource.createFeedback(retrospectionId).toDomain())
    }

    override fun getPrincipleGroups(orderType: String): Flow<List<MyPrincipleGroup>> = flow {
        emit(remoteDataSource.getPrinciples(orderType).toDomain())
    }

    override suspend fun uploadImageUri(
        domain: String,
        uri: String,
        fileName: String?
    ): Int = remoteDataSource.uploadImageUri(domain, uri, fileName)
}
