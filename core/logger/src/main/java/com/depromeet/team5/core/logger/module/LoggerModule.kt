package com.depromeet.team5.core.logger.module

import com.depromeet.team5.core.logger.Logger
import com.depromeet.team5.core.logger.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoggerModule {


    @Singleton
    @Binds
    abstract fun bindLogger(loggerImpl: LoggerImpl): Logger
}
