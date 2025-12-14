package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.TokenRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper


data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
) : RetrofitMapper<TokenRemoteData> {
    override fun toRemoteData(): TokenRemoteData {
        return TokenRemoteData(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}
