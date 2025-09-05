package com.depromeet.team5.core.domain.usecase

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TestUseCase @Inject constructor() {

    suspend operator fun invoke() = coroutineScope {
        delay(1000)

        "Hello Domain World"
    }
}
