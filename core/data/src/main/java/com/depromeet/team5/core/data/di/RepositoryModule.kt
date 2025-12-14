package com.depromeet.team5.core.data.di

import com.depromeet.team5.core.data.repositoryimpl.BadgeDotRepositoryImpl
import com.depromeet.team5.core.data.repositoryimpl.HedgeRepositoryImpl
import com.depromeet.team5.core.data.repositoryimpl.LoginRepositoryImpl
import com.depromeet.team5.core.data.repositoryimpl.RetrospectionRepositoryImpl
import com.depromeet.team5.core.domain.repository.BadgeDotRepository
import com.depromeet.team5.core.domain.repository.HedgeRepository
import com.depromeet.team5.core.domain.repository.LoginRepository
import com.depromeet.team5.core.domain.repository.RetrospectionRepository
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

    @Binds
    @Singleton
    abstract fun bindRetrospectionRepository(
        retrospectionRepositoryImpl: RetrospectionRepositoryImpl
    ): RetrospectionRepository

    @Binds
    @Singleton
    abstract fun bindBadgeDotRepository(
        badgeDotRepositoryImpl: BadgeDotRepositoryImpl
    ): BadgeDotRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository
}
