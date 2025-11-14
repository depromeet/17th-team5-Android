package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject

class GetFeedbackUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {
    operator fun invoke(retrospectionId: Int) = hedgeRepository.getFeedback(retrospectionId)
}
