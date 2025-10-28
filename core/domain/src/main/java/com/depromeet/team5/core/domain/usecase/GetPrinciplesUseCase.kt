package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class GetPrinciplesUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    operator fun invoke(orderType: String) = hedgeRepository.getPrinciples(orderType)
}
