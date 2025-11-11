package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.BaseDomain
import com.depromeet.team5.core.domain.model.PrincipleState
import com.depromeet.team5.core.domain.model.Retrospection
import com.depromeet.team5.core.domain.repository.RetrospectionRepository
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import com.depromeet.team5.core.domain.request.PrincipleCheckRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CreateRetrospectionUseCase @Inject constructor(
    private val retrospectionRepository: RetrospectionRepository,
    private val updateImageUrisUseCase: UpdateImageUrisUseCase
) {
    suspend operator fun invoke(
        request: CreateRetrospectionRequest,
        principles: List<PrincipleState>,
    ): Flow<BaseDomain<Retrospection>> = withContext(Dispatchers.IO) {
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
        retrospectionRepository.createRetrospection(request.copy(principleChecks = principleChecks))
    }
}