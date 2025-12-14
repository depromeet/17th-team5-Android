package com.depromeet.team5.core.retrofit.di

import com.depromeet.team5.core.retrofit.di.qualifier.RegularInterceptorQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object OkHttpClientModule {

    private const val NETWORK_TIMEOUT = 60L

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @RegularInterceptorQualifier
        interceptor: Interceptor,
        authenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .authenticator(authenticator)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}