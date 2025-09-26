package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class CreateRetrospectionUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    operator fun invoke(
        body: Map<String, Any?>
    ) = hedgeRepository.createRetrospection(body)

}
