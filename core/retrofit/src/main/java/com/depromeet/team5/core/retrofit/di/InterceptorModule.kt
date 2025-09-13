package com.depromeet.team5.core.retrofit.di

import com.depromeet.team5.core.retrofit.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object InterceptorModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    //for test
    @Provides
    @Singleton
    fun provideMockInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            if (request.url.encodedPath == "/test") {
                val body = """{"print":"Hello from Mock!"}"""
                Response.Builder()
                    .code(200)
                    .protocol(Protocol.HTTP_1_1)
                    .message("OK")
                    .request(request)
                    .body(body.toResponseBody("application/json".toMediaType()))
                    .build()
            } else {
                chain.proceed(request)
            }
        }
    }
}