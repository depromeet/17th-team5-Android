package com.depromeet.team5.core.remotedatasource.apisource

import com.depromeet.team5.core.remotedatasource.model.TestRemoteResponse

interface TestApiSource {
    suspend fun getTest(): TestRemoteResponse
}