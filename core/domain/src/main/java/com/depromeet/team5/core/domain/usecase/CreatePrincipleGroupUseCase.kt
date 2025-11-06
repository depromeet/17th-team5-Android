package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class CreatePrincipleGroupUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    operator fun invoke(myPrincipleGroup: MyPrincipleGroup) = run {
        val map = mapOf(
            "groupName" to myPrincipleGroup.groupName,
            "thumbnail" to myPrincipleGroup.thumbnail,
            "principleType" to myPrincipleGroup.orderType.name,
            "principles" to myPrincipleGroup.principles.map {
                mapOf(
                    "principle" to it.principle,
                    "description" to it.description
                )
            }
        )

        hedgeRepository.createPrincipleGroup(map)
    }
}