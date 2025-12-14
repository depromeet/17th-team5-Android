package com.depromeet.team5.core.data.datasource


interface LocalDataSource {
    suspend fun isConsumed(id: Int): Boolean
    suspend fun consume(id: Int): Boolean

    suspend fun setAccessToken(token: String?): String?

    suspend fun getAccessToken(): String?

    suspend fun setRefreshToken(token: String?): String?

    suspend fun getRefreshToken(): String?
}
