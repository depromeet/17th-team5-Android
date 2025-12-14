package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class SetTokensUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String
    ) = coroutineScope {
        launch(Dispatchers.IO) {
            loginRepository.setAccessToken(accessToken)
        }

        launch(Dispatchers.IO) {
            loginRepository.setAccessToken(refreshToken)
        }
    }
}