package com.depromeet.team5.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.depromeet.team5.core.domain.repository.LoginRepository
import com.depromeet.team5.core.domain.usecase.RefreshAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase
) : ViewModel() {

    val eventBus = Channel<NavigationType>()


    init {
        viewModelScope.launch {
            val accessToken = loginRepository.getAccessToken()

            if (accessToken == null) {
                eventBus.trySendBlocking(NavigationType.LOGIN)
                return@launch
            }

            if (isExpired(accessToken)) {
                val refreshToken = loginRepository.getRefreshToken()

                if (refreshToken == null) {
                    eventBus.trySendBlocking(NavigationType.LOGIN)
                    return@launch
                }

                if (isExpired(refreshToken)) {
                    eventBus.trySendBlocking(NavigationType.LOGIN)
                } else {
                    refreshAccessTokenUseCase(refreshToken)
                    eventBus.trySendBlocking(NavigationType.HOME)
                }
            } else {
                eventBus.trySendBlocking(NavigationType.HOME)
            }
        }
    }

    private fun isExpired(token: String): Boolean = run {
        val jwt = JWT(token)
        val expiresAt = jwt.expiresAt

        expiresAt == null || expiresAt.time < System.currentTimeMillis()
    }
}