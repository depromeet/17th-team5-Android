package com.depromeet.team5.core.ui.util

import android.util.Base64
import com.depromeet.team5.core.ui.model.JwtToken
import kotlinx.serialization.json.Json
import java.nio.charset.Charset


object JwtParser {

    private val json = Json { ignoreUnknownKeys = true }

    fun decodePayload(token: String): JwtToken? {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return null

            val payloadBytes = Base64.decode(parts[1], Base64.URL_SAFE)
            val payloadJsonString = String(payloadBytes, Charset.defaultCharset())

            json.decodeFromString<JwtToken>(payloadJsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
