package com.depromeet.team5.core.domain.model

sealed class SocialLogin {
    data class Success(
        val userId: Long?,
        val nickname: String?,
        val email: String?,
        val profileImageUrl: String?,
        val isNewUser: Boolean?,
        val accessToken: String?,
        val refreshToken: String?
    ) : SocialLogin()

    data class Failure(
        val code: String?,
        val message: String?,
        val data: SocialLoginFailure?
    ) : SocialLogin()
}

data class SocialLoginFailure(
    val provider: String?,
    val httpStatus: Int?,
    val error: String?,
    val errorDescription: String?,
    val errorCode: String?
)

