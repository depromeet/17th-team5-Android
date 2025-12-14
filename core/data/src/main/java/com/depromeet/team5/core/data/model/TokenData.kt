package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.Token

data class TokenData(
    val accessToken: String,
    val refreshToken: String
) : DataMapper<Token> {
    override fun toDomain(): Token {
        return Token(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
