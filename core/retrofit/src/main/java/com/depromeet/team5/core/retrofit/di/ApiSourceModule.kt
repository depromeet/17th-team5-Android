package com.depromeet.team5.core.retrofit.di

import com.depromeet.team5.core.remotedatasource.apisource.HedgeApiSource
import com.depromeet.team5.core.retrofit.apisourceimpl.HedgeApiSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal abstract class ApiSourceModule {

    @Binds
    @Singleton
    abstract fun bindHedgeApiSource(
        hedgeApiSourceImpl: HedgeApiSourceImpl
    ): HedgeApiSource
}
