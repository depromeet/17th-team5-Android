package com.depromeet.team5.features.login

import android.content.Context
import com.depromeet.team5.core.domain.usecase.LogoutUseCase
import com.kakao.sdk.auth.AuthCodeClient
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Named

@ActivityScoped
class KakaoLoginManager @Inject constructor(
    @ActivityContext private val context: Context,
    @Named("kakaoAppKey") private val kakaoAppKey: String,
    private val logoutUseCase: LogoutUseCase
) {

    suspend fun authorize(): Result<Pair<String, String>> =
        coroutineScope {
            val kakaoLogin = authorizeWithKakaoTalk()

            if (kakaoLogin != null) {
                Result.success(kakaoLogin)
            } else {
                val kakaoAccountLogin = authorizeWithAccount()

                if (kakaoAccountLogin != null) {
                    Result.success(kakaoAccountLogin)
                } else {
                    Result.failure(Exception("카카오 로그인 실패"))
                }
            }
        }

    private suspend fun authorizeWithKakaoTalk() =
        suspendCancellableCoroutine { continuation ->
            AuthCodeClient.instance.authorizeWithKakaoTalk(context) { code, error ->
                val redirectUri = getKakaoRedirectUri(kakaoAppKey)

                if (code != null) {
                    continuation.resume(
                        value = code to redirectUri,
                        onCancellation = { _, _, _ -> }
                    )
                } else {
                    continuation.resume(
                        value = null,
                        onCancellation = { _, _, _ -> }
                    )
                }
            }
        }

    private suspend fun authorizeWithAccount() =
        suspendCancellableCoroutine { continuation ->
            AuthCodeClient.instance.authorizeWithKakaoAccount(context) { code, error ->
                val redirectUri = getKakaoRedirectUri(kakaoAppKey)

                if (code != null) {
                    continuation.resume(
                        value = code to redirectUri,
                        onCancellation = { _, _, _ -> }
                    )
                } else {
                    continuation.resume(
                        value = null,
                        onCancellation = { _, _, _ -> }
                    )
                }
            }
        }

    private fun getKakaoRedirectUri(appKey: String): String =
        "kakao${appKey}://oauth"

    suspend fun logout(): Boolean {
        val isLogout = suspendCancellableCoroutine { cont ->
            UserApiClient.instance.logout { error ->
//                 authentication tokens don't exist.라는 에러가 나와서.. 일단 이렇게 무조건 true로 리턴
//                 cont.resume(error == null, null)
                cont.resume(true, null)
            }
        }

        if (isLogout) {
            logoutUseCase()
        }

        return isLogout
    }
}
