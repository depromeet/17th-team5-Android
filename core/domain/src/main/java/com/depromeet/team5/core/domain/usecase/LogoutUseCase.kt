package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.LoginRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke() {
        loginRepository.setAccessToken(null)
        loginRepository.setRefreshToken(null)

    }
}