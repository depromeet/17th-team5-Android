package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.UserStats
import com.depromeet.team5.core.domain.repository.HedgeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserStatsUseCase @Inject constructor(
    private val repository: HedgeRepository
) {

    operator fun invoke(): Flow<UserStats> = repository.userStats()
}