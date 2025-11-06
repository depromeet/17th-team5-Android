package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import javax.inject.Inject


class CreateRetrospectionUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    operator fun invoke(
        request: CreateRetrospectionRequest
    ) = hedgeRepository.createRetrospection(request)

}
