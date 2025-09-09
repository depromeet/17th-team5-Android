package com.depromeet.team5.core.data.datasource

import com.depromeet.team5.core.data.model.TestData


interface RemoteDataSource {

    suspend fun getData(): TestData
}
