package com.depromeet.team5.features.login

import android.content.Context
import com.kakao.sdk.auth.AuthCodeClient
import javax.inject.Inject

object KakaoRedirectUri {
    fun value(appKey: String): String = "kakao${appKey}://oauth"
}

class KakaoAuthCodeManager @Inject constructor() {

    fun authorize(
        context: Context,
        appKey: String,
        onResult: (Result<Pair<String, String>>) -> Unit,
    ) {
        val redirectUri = KakaoRedirectUri.value(appKey)

        AuthCodeClient.instance.authorizeWithKakaoTalk(context) { code, error ->
            when {
                code != null -> {
                    onResult(Result.success(code to redirectUri))
                }

                else -> authorizeWithAccount(context, redirectUri, onResult)
            }
        }
    }

    private fun authorizeWithAccount(
        context: Context,
        redirectUri: String,
        onResult: (Result<Pair<String, String>>) -> Unit,
    ) {
        AuthCodeClient.instance.authorizeWithKakaoAccount(context) { code, error ->
            if (code != null) {
                onResult(Result.success(code to redirectUri))
            } else {
                onResult(Result.failure(error ?: Exception("Kakao authorize failed")))
            }
        }
    }
}
