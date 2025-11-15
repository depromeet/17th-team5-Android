package com.depromeet.team5.core.ui.model

import kotlinx.serialization.Serializable


@Serializable
data class JwtToken(
    val sub: String,
    val iat: Long,
    val exp: Long
)
