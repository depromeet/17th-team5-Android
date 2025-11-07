package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.repository.HedgeRepository
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import com.depromeet.team5.core.domain.request.PrincipleCheckRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class CreateRetrospectionUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository,
    private val updateImageUriUseCase: UpdateImageUriUseCase
) {

    suspend operator fun invoke(
        request: CreateRetrospectionRequest,
        principles: List<MyPrinciple>
    ) = run {
        val list = mutableListOf<PrincipleCheckRequest>()

        principles.forEach { principle ->
            if (principle.principleChecks.imageUrls.isEmpty()) return@forEach

            val ids = withContext(Dispatchers.Unconfined) {
                updateImageUriUseCase(
                    domain = "retrospection",
                    imageUrls = principle.principleChecks.imageUrls,
                    fileName = null
                )
            }

            val principleCheckRequest = PrincipleCheckRequest(
                principleId = principle.id,
                status = principle.principleChecks.status,
                reason = principle.principleChecks.reason,
                imageUrls = ids,
                links = principle.principleChecks.links
            )

            list.add(principleCheckRequest)
        }

        hedgeRepository.createRetrospection(request.copy(principleChecks = list))
    }

}
