package com.depromeet.team5.core.domain.repository

import com.depromeet.team5.core.domain.model.Token


interface LoginRepository {

    suspend fun setAccessToken(token: String): String?

    suspend fun getAccessToken(): String?

    suspend fun setRefreshToken(token: String): String?

    suspend fun getRefreshToken(): String?

    suspend fun refreshAccessToken(body: Map<String, Any?>): Token
}
