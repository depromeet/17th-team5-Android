package com.depromeet.team5.core.retrofit.di

import com.auth0.android.jwt.JWT
import com.depromeet.team5.core.datastore.LoginDataStore
import com.depromeet.team5.core.retrofit.SessionManager
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenAuthenticator @Inject constructor(
    private val dataStore: LoginDataStore,
    private val sessionManager: SessionManager
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {

        mutex.withLock {
            val accessToken = dataStore.getAccessToken() ?: run {
                updateSignal()
                return@runBlocking null
            }

            if (isExpired(accessToken)) {
                updateSignal()
                return@runBlocking null

            } else {
                return@runBlocking response.request.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                    .build()
            }
        }
    }

    private fun isExpired(token: String): Boolean = run {
        val jwt = JWT(token)
        val expiresAt = jwt.expiresAt

        expiresAt == null || expiresAt.time < System.currentTimeMillis()
    }

    private fun updateSignal() {
        sessionManager.loginEvent.trySendBlocking(Unit)
    }

}
