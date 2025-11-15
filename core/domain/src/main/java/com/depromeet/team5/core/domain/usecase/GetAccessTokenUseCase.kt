package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.JwtTokenType
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.repository.LoginRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class GetAccessTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(parse: (String) -> Long): HedgeUiState<JwtTokenType> {
        loginRepository.getAccessToken()
            .fold(
                onSuccess = { token ->
                    token?.let {
                        //1763140254
                        val exp = TimeUnit.SECONDS.toMillis(parse(token))

                        val currentTimeMillis = System.currentTimeMillis()
                        val currentTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis)

                        if (currentTimeSeconds > exp) {
                            loginRepository.getRefreshToken()
                                .fold(
                                    onSuccess = { refreshToken ->
                                        refreshToken?.let {
                                            val refreshExp = TimeUnit.SECONDS.toMillis(parse(it))

                                            if (currentTimeSeconds > refreshExp) {
                                                HedgeUiState.Success(JwtTokenType.EXPIRE)
                                            } else {
                                                HedgeUiState.Success(JwtTokenType.UNEXPIRED)
                                            }
                                        }

                                    },
                                    onFailure = {
                                        return HedgeUiState.Error(throwable = it)
                                    }
                                )
                        } else {
                            return HedgeUiState.Success(JwtTokenType.UNEXPIRED)
                        }
                    }
                },
                onFailure = {
                    return HedgeUiState.Error(throwable = it)
                }
            )

        return HedgeUiState.Error(throwable = Exception("AccessToken is invalid"))
    }
}
