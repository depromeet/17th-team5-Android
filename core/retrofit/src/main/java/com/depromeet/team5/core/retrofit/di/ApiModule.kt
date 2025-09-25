package com.depromeet.team5.core.retrofit.di

import com.depromeet.team5.core.retrofit.api.HedgeApi
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
        retrofit: Retrofit
    ): HedgeApi = retrofit.create(HedgeApi::class.java)

}
