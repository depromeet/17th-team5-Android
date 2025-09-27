package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.AnalysisEntity
import com.depromeet.team5.core.domain.repository.HedgeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateAnalysisUseCase @Inject constructor(
    private val repository: HedgeRepository
) {
    operator fun invoke(
        market: String,
        symbol: String,
        time: String,
    ): Flow<AnalysisEntity> = repository.createAnalysis(
        mapOf(
            "market" to market,
            "symbol" to symbol,
            "time" to time,
        )
    )
}
