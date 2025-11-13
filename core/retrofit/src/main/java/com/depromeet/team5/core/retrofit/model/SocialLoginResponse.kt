package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.SocialLoginFailureRemoteData
import com.depromeet.team5.core.remotedatasource.model.SocialLoginRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginSuccessResponse(
    val userId: Long?,
    val nickname: String?,
    val email: String?,
    val profileImageUrl: String?,
    val isNewUser: Boolean?,
    val accessToken: String?,
    val refreshToken: String?,
) : RetrofitMapper<SocialLoginRemoteData.Success> {
    override fun toRemoteData(): SocialLoginRemoteData.Success =
        SocialLoginRemoteData.Success(
            userId, nickname, email, profileImageUrl, isNewUser, accessToken, refreshToken
        )
}

@Serializable
data class SocialLoginFailureResponse(
    val code: String? = null,
    val message: String? = null,
    val data: SocialLoginFailureDetailResponse? = null,
) : RetrofitMapper<SocialLoginRemoteData.Failure> {
    override fun toRemoteData(): SocialLoginRemoteData.Failure =
        SocialLoginRemoteData.Failure(
            code = code,
            message = message,
            data = data?.toRemoteData()
        )
}

@Serializable
data class SocialLoginFailureDetailResponse(
    val provider: String? = null,
    val httpStatus: Int? = null,
    val error: String? = null,
    @SerialName("error_description") val errorDescription: String? = null,
    @SerialName("error_code") val errorCode: String? = null,
) : RetrofitMapper<SocialLoginFailureRemoteData> {
    override fun toRemoteData(): SocialLoginFailureRemoteData =
        SocialLoginFailureRemoteData(provider, httpStatus, error, errorDescription, errorCode)
}
