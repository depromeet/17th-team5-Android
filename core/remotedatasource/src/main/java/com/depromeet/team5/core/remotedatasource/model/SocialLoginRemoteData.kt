package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.SocialLoginData
import com.depromeet.team5.core.data.model.SocialLoginFailureData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

sealed class SocialLoginRemoteData : RemoteDataMapper<SocialLoginData> {

    data class Success(
        val userId: Long?,
        val nickname: String?,
        val email: String?,
        val profileImageUrl: String?,
        val isNewUser: Boolean?,
        val accessToken: String?,
        val refreshToken: String?
    ) : SocialLoginRemoteData() {
        override fun toData(): SocialLoginData =
            SocialLoginData.Success(
                userId, nickname, email, profileImageUrl, isNewUser, accessToken, refreshToken
            )
    }

    data class Failure(
        val code: String?,
        val message: String?,
        val data: SocialLoginFailureRemoteData?
    ) : SocialLoginRemoteData() {
        override fun toData(): SocialLoginData =
            SocialLoginData.Failure(code, message, data?.toData())
    }
}

data class SocialLoginFailureRemoteData(
    val provider: String?,
    val httpStatus: Int?,
    val error: String?,
    val errorDescription: String?,
    val errorCode: String?
) : RemoteDataMapper<SocialLoginFailureData> {
    override fun toData(): SocialLoginFailureData =
        SocialLoginFailureData(provider, httpStatus, error, errorDescription, errorCode)
}