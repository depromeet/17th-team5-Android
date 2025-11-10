package com.depromeet.team5.core.data.repositoryimpl

import com.depromeet.team5.core.data.datasource.RetrospectionRemoteDataSource
import com.depromeet.team5.core.domain.repository.RetrospectionRepository
import javax.inject.Inject


internal class RetrospectionRepositoryImpl @Inject constructor(
    private val retrospectionRemoteDataSource: RetrospectionRemoteDataSource,
) : RetrospectionRepository {
}
