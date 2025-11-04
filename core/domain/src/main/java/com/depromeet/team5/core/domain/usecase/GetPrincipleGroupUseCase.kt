package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class GetPrincipleGroupUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    operator fun invoke(groupId: Int) = hedgeRepository.getPrincipleGroup(groupId)

}
