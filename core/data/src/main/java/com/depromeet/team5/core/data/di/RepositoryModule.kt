package com.depromeet.team5.core.data.di

import com.depromeet.team5.core.data.repositoryimpl.TestRepositoryImpl
import com.depromeet.team5.core.domain.repository.TestRepository
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
        testRepositoryImpl: TestRepositoryImpl
    ): TestRepository
}
