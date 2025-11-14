package com.depromeet.team5.core.data.datasource


interface LocalDataSource {
    suspend fun isConsumed(id: Int): Boolean
    suspend fun consume(id: Int): Boolean

    suspend fun setAccessToken(token: String): Result<String?>

    suspend fun getAccessToken(): Result<String?>

    suspend fun setRefreshToken(token: String): Result<String?>

    suspend fun getRefreshToken(): Result<String?>
}
