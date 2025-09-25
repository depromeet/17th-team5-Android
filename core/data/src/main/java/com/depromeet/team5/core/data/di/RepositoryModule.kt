package com.depromeet.team5.core.data.di

import com.depromeet.team5.core.data.repositoryimpl.HedgeRepositoryImpl
import com.depromeet.team5.core.domain.repository.HedgeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideTestRepository(
        hedgeRepositoryImpl: HedgeRepositoryImpl
    ): HedgeRepository
}
