package com.depromeet.team5.core.data.repositoryimpl

import com.depromeet.team5.core.data.datasource.LocalDataSource
import com.depromeet.team5.core.domain.repository.BadgeDotRepository
import javax.inject.Inject

internal class BadgeDotRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : BadgeDotRepository {
    override suspend fun isConsumed(retrospectionId: Int): Boolean =
        localDataSource.isConsumed(retrospectionId)

    override suspend fun consume(retrospectionId: Int): Boolean =
        localDataSource.consume(retrospectionId)
}