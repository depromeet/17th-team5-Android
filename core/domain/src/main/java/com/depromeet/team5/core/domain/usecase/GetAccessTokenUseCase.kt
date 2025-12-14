package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetAccessTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(): Flow<String?> = flow {
        loginRepository.getAccessToken()
    }
}
