package com.depromeet.team5.core.model

import com.depromeet.team5.core.data.model.TestData
import com.depromeet.team5.core.mapper.DataMapper


data class TestLocalData(
    val print: String
) : DataMapper<TestData> {

    override fun toData(): TestData = TestData(
        print = print
    )

}
