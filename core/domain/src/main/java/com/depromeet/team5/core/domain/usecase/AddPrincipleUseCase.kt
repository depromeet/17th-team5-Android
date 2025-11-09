package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class AddPrincipleUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    operator fun invoke(
        groupId: Int,
        principle: String,
        description: String
    ) = hedgeRepository.addPrinciple(
        groupId = groupId,
        principle = principle,
        description = description
    )
}
