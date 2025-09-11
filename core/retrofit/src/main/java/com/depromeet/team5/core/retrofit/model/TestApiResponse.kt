package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.TestRemoteResponse
import com.depromeet.team5.core.retrofit.mapper.ResponseMapper
import kotlinx.serialization.Serializable

@Serializable
internal data class TestApiResponse(
    val print: String
) : ResponseMapper<TestRemoteResponse> {
    override fun toRemoteResponse(): TestRemoteResponse = TestRemoteResponse(print)
}
