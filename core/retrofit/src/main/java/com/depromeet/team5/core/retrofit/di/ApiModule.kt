package com.depromeet.team5.core.retrofit.di

import com.depromeet.team5.core.retrofit.api.HedgeApi
import com.depromeet.team5.core.retrofit.api.LoginApi
import com.depromeet.team5.core.retrofit.api.RetrospectionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideHedgeApi(
        @GsonRetrofit retrofit: Retrofit
    ): HedgeApi = retrofit.create(HedgeApi::class.java)

    @Provides
    @Singleton
    fun provideRetrospectionApi(
        @SerializationRetrofit retrofit: Retrofit
    ): RetrospectionApi = retrofit.create(RetrospectionApi::class.java)

    @Provides
    @Singleton
    fun provideLoginApi(
        @GsonRetrofit retrofit: Retrofit
    ): LoginApi = retrofit.create(LoginApi::class.java)

}
