package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.SocialLogin
import com.depromeet.team5.core.domain.repository.HedgeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SocialLoginUseCase @Inject constructor(
    private val repository: HedgeRepository
){
    operator fun invoke(
        provider: String,
        authCode: String,
        redirectUri: String,
        email: String? = null,
        nickname: String? = null
    ): Flow<SocialLogin> = repository.socialLogin(
        provider = provider,
        authCode = authCode,
        redirectUri = redirectUri,
        email = email,
        nickname = nickname
    )
}