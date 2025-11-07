package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.PrincipleState
import com.depromeet.team5.core.domain.repository.HedgeRepository
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import com.depromeet.team5.core.domain.request.PrincipleCheckRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CreateRetrospectionUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository,
    private val updateImageUrisUseCase: UpdateImageUrisUseCase
) {
    suspend operator fun invoke(
        request: CreateRetrospectionRequest,
        principles: List<PrincipleState>,
    ) = withContext(Dispatchers.IO) {
        val principleChecks = principles.map {
            PrincipleCheckRequest(
                principleId = it.id,
                status = it.principleChecks.status,
                reason = it.principleChecks.reason,
                imageIds = withContext(Dispatchers.Unconfined) {
                    updateImageUrisUseCase(
                        domain = "retrospection",
                        imageUrls = it.principleChecks.imageUrls,
                        fileName = null
                    )
                },
                links = it.principleChecks.links
            )
        }
        hedgeRepository.createRetrospection(request.copy(principleChecks = principleChecks))
    }
}