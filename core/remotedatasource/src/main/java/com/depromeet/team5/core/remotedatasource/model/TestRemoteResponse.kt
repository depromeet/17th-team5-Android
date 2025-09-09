package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.TestData
import com.depromeet.team5.core.remotedatasource.mapper.DataMapper


data class TestRemoteResponse(
    val print: String
) : DataMapper<TestData> {

    override fun toData(): TestData = TestData(print)
}
