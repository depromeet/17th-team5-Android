package com.depromeet.team5.core.retrofit.apisourceimpl

import com.depromeet.team5.core.remotedatasource.apisource.TestApiSource
import com.depromeet.team5.core.remotedatasource.model.TestRemoteResponse
import com.depromeet.team5.core.retrofit.api.TestApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TestApiSourceImpl @Inject constructor(
    private val testApi: TestApi,
) : TestApiSource {
    override suspend fun getTest(): TestRemoteResponse {
        return testApi
            .getTest()
            .body()!!
            .toRemoteResponse()
    }
}