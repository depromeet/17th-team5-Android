package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class CreateFeedbackUseCase @Inject constructor(
    private val repository: HedgeRepository
) {

    operator fun invoke(
        retrospectionId: Int,
        body: Map<String, Any?>
    ) = repository.createFeedback(retrospectionId, body)

}
