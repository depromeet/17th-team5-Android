package com.depromeet.team5.core

import com.depromeet.team5.core.data.datasource.LocalDataSource
import com.depromeet.team5.core.data.model.TestData
import com.depromeet.team5.core.model.TestLocalData
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class LocalDataSourceImpl @Inject constructor() : LocalDataSource {

    override suspend fun getData(): TestData = coroutineScope {
        TestLocalData("Hello LocalDataSource World").toData()
    }
}
