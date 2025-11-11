package com.depromeet.team5.di

import com.depromeet.team5.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppKeysModule {

    @Provides
    @Singleton
    @Named("kakaoAppKey")
    fun provideKakaoAppKey(): String = BuildConfig.KAKAO_NATIVE_APP_KEY
}