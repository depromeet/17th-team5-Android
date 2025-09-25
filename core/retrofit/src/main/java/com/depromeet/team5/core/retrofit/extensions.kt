package com.depromeet.team5.core.retrofit

import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody


fun Map<String, Any?>.toRequestBody() = Gson().toJson(this).toRequestBody()
