package com.depromeet.team5.core.logger.module

import com.depromeet.team5.core.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object LoggerModule {


    @Provides
    @Singleton
    fun provideLogger() = Logger
}
