package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class GetPrincipleGroupsUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    operator fun invoke(orderType: OrderType) =
        hedgeRepository.getPrincipleGroups(orderType.name)
}
