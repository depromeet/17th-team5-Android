package com.depromeet.team5.core.remotedatasource.datasourceimpl

import com.depromeet.team5.core.data.datasource.RetrospectionRemoteDataSource
import com.depromeet.team5.core.remotedatasource.apisource.RetrospectionApiSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RetrospectionRemoteDataSourceImpl @Inject constructor(
    private val retrospectionApiSource: RetrospectionApiSource,
) : RetrospectionRemoteDataSource {
}
