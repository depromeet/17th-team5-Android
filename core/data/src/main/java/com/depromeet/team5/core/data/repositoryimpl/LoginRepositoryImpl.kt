package com.depromeet.team5.core.data.repositoryimpl

import com.depromeet.team5.core.data.datasource.LocalDataSource
import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.domain.model.Token
import com.depromeet.team5.core.domain.repository.LoginRepository
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : LoginRepository {

    override suspend fun setAccessToken(token: String): String? =
        localDataSource.setAccessToken(token)

    override suspend fun getAccessToken(): String? =
        localDataSource.getAccessToken()

    override suspend fun setRefreshToken(token: String): String? =
        localDataSource.setRefreshToken(token)

    override suspend fun getRefreshToken(): String? =
        localDataSource.getRefreshToken()

    override suspend fun refreshAccessToken(body: Map<String, Any?>): Token =
        remoteDataSource.refreshAccessToken(body).toDomain()
}
