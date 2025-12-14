package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.TokenData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

data class TokenRemoteData(
    val accessToken: String,
    val refreshToken: String
) : RemoteDataMapper<TokenData> {
    override fun toData(): TokenData {
        return TokenData(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
