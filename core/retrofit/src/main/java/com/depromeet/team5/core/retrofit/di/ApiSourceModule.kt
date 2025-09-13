package com.depromeet.team5.core.retrofit.di

import com.depromeet.team5.core.remotedatasource.apisource.TestApiSource
import com.depromeet.team5.core.retrofit.apisourceimpl.TestApiSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ApiSourceModule {

    @Binds
    @Singleton
    fun bindApiSource(
        testApiSourceImpl: TestApiSourceImpl
    ): TestApiSource
}