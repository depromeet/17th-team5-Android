package com.depromeet.team5.core.remotedatasource.di

import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.datasource.RetrospectionRemoteDataSource
import com.depromeet.team5.core.remotedatasource.RemoteDataSourceImpl
import com.depromeet.team5.core.remotedatasource.datasourceimpl.RetrospectionRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRetrospectionRemoteDataSource(
        retrospectionRemoteDataSourceImpl: RetrospectionRemoteDataSourceImpl
    ): RetrospectionRemoteDataSource
}
