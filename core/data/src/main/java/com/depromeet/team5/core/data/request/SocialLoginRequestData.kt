package com.depromeet.team5.core.data.request

data class SocialLoginRequestData(
    val provider: String,
    val authCode: String,
    val redirectUri: String,
    val email: String?,
    val nickname: String?
)