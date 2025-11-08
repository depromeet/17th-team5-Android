package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class ModifyPrincipleUseCase @Inject constructor(
    private val repository: HedgeRepository
) {

    operator fun invoke(
        principleId: Int,
        principle: String,
        description: String
    ) = repository.modifyPrinciple(
        principleId = principleId,
        principle = principle,
        description = description
    )
}
