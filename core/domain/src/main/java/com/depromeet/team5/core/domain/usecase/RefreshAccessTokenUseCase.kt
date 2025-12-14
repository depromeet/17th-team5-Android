package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.LoginRepository
import javax.inject.Inject


class RefreshAccessTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(refreshToken: String) {
        val response = loginRepository.refreshAccessToken(
            mapOf(
                "refreshToken" to refreshToken
            )
        )

        loginRepository.setAccessToken(response.accessToken)
        loginRepository.setRefreshToken(response.refreshToken)
    }
}
