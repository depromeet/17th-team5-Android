package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.BadgeDotRepository
import javax.inject.Inject

class CheckAndConsumeBadgeDotUseCase @Inject constructor(
    private val badgeDotRepository: BadgeDotRepository
) {

    suspend operator fun invoke(incomingId: Int?): Int? {
        if (incomingId == null) return null
        val consumed = badgeDotRepository.isConsumed(incomingId)
        return if (consumed) {
            null
        } else {
            badgeDotRepository.consume(incomingId)
            incomingId
        }
    }
}
