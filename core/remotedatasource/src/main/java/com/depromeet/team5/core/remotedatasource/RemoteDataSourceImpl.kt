package com.depromeet.team5.core.remotedatasource

import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.model.TestData
import com.depromeet.team5.core.remotedatasource.apisource.TestApiSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RemoteDataSourceImpl @Inject constructor(
    private val testApiSource: TestApiSource,
) : RemoteDataSource {

    override suspend fun getData(): TestData = testApiSource.getTest().toData()
}
