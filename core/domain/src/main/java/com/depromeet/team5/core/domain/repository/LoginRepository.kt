package com.depromeet.team5.core.domain.repository


interface LoginRepository {

    suspend fun setAccessToken(token: String): Result<String?>

    suspend fun getAccessToken(): String?

    suspend fun setRefreshToken(token: String): Result<String?>

    suspend fun getRefreshToken(): Result<String?>
}
