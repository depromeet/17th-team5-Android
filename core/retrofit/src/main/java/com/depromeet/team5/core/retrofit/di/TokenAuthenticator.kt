package com.depromeet.team5.core.retrofit.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.auth0.android.jwt.JWT
import com.depromeet.team5.core.preferencekey.PreferenceKey
import com.depromeet.team5.core.retrofit.SessionManager
import com.depromeet.team5.core.retrofit.api.LoginApi
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
    private val dataStore: DataStore<Preferences>,
    private val loginApi: LoginApi,
    private val sessionManager: SessionManager
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {

        mutex.withLock {
            val accessToken = dataStore.data.map { preferences ->
                preferences[PreferenceKey.LOGIN_ACCESS_TOKEN]
            }.first() ?: run {
                logout()
                return@runBlocking null
            }

            if (isExpired(accessToken)) {
                val refreshToken = dataStore.data.map { preferences ->
                    preferences[PreferenceKey.LOGIN_REFRESH_TOKEN]
                }.first() ?: run {
                    logout()
                    return@runBlocking null
                }

                val accessToken = updateToken(refreshToken)

                return@runBlocking response.request.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                    .build()

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

    private suspend fun updateToken(token: String) = run {
        val response = loginApi.refreshAccessToken(
            mapOf("refreshToken" to token)
        )

        dataStore.edit { preferences ->
            preferences[PreferenceKey.LOGIN_ACCESS_TOKEN] = response.accessToken
            preferences[PreferenceKey.LOGIN_REFRESH_TOKEN] = response.refreshToken
        }[PreferenceKey.LOGIN_ACCESS_TOKEN]
    }

    private fun logout() {
        sessionManager.loginEvent.trySendBlocking(Unit)
    }
}
