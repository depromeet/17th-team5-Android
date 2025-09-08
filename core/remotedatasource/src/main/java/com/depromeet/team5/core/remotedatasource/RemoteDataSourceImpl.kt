package com.depromeet.team5.core.remotedatasource

import com.depromeet.team5.core.data.datasource.RemoteDataSource
import com.depromeet.team5.core.data.model.TestData
import com.depromeet.team5.core.remotedatasource.model.TestRemoteResponse
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RemoteDataSourceImpl @Inject constructor() : RemoteDataSource {

    override suspend fun getData(): TestData = coroutineScope {
        TestRemoteResponse("Hello RemoteDataSource World").toData()
    }
}
