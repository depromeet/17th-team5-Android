package com.depromeet.team5.core

import com.depromeet.team5.core.data.datasource.LocalDataSource
import com.depromeet.team5.core.datastore.BadgeDataStore
import com.depromeet.team5.core.datastore.LoginDataStore
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class LocalDataSourceImpl @Inject constructor(
    private val badgeDataStore: BadgeDataStore,
    private val loginDataStore: LoginDataStore
) : LocalDataSource {

    override suspend fun isConsumed(id: Int): Boolean =
        badgeDataStore.isConsumed(id)

    override suspend fun consume(id: Int): Boolean =
        badgeDataStore.consume(id)

    override suspend fun setAccessToken(token: String?): String? =
        loginDataStore.setAccessToken(token)

    override suspend fun getAccessToken(): String? = loginDataStore.getAccessToken()

    override suspend fun setRefreshToken(token: String?): String? =
        loginDataStore.setRefreshToken(token)

    override suspend fun getRefreshToken(): String? = loginDataStore.getRefreshToken()
}
