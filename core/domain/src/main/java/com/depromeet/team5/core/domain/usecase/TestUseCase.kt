package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.TestRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TestUseCase @Inject constructor(
    private val testRepository: TestRepository
) {

    suspend operator fun invoke() = testRepository.print()
}
