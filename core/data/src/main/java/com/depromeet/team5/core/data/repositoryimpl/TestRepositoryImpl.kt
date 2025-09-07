package com.depromeet.team5.core.data.repositoryimpl

import com.depromeet.team5.core.domain.repository.TestRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject


internal class TestRepositoryImpl @Inject constructor() : TestRepository {


    override suspend fun print(): String = coroutineScope {
        delay(2000)

        "Hello Repository World"
    }
}
