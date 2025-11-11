package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.SocialLogin
import com.depromeet.team5.core.domain.model.SocialLoginFailure

sealed class SocialLoginData : DataMapper<SocialLogin> {

    data class Success(
        val userId: Long?,
        val nickname: String?,
        val email: String?,
        val profileImageUrl: String?,
        val isNewUser: Boolean?,
        val accessToken: String?,
        val refreshToken: String?
    ) : SocialLoginData() {
        override fun toDomain(): SocialLogin =
            SocialLogin.Success(userId, nickname, email, profileImageUrl, isNewUser, accessToken, refreshToken)
    }

    data class Failure(
        val code: String?,
        val message: String?,
        val data: SocialLoginFailureData?
    ) : SocialLoginData() {
        override fun toDomain(): SocialLogin =
            SocialLogin.Failure(code, message, data?.toDomain())
    }
}

data class SocialLoginFailureData(
    val provider: String?,
    val httpStatus: Int?,
    val error: String?,
    val errorDescription: String?,
    val errorCode: String?
) : DataMapper<SocialLoginFailure> {
    override fun toDomain(): SocialLoginFailure =
        SocialLoginFailure(provider, httpStatus, error, errorDescription, errorCode)
}
