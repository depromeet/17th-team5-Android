package com.depromeet.team5.core.remotedatasource.request

data class SocialLoginRequestRemoteData(
    val provider: String,
    val authCode: String,
    val redirectUri: String,
    val email: String?,
    val nickname: String?
)