package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class CreatePrincipleGroupUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    operator fun invoke(myPrincipleGroup: MyPrincipleGroup) = run {
        //url 업로드는 imageId로 업로드 해야함.
        val targetThumbnail = if (
            myPrincipleGroup.thumbnail.startsWith("http") ||
            myPrincipleGroup.thumbnail.startsWith("https")
        ) {
            myPrincipleGroup.imageId
        } else {
            myPrincipleGroup.thumbnail
        }

        val map = mapOf(
            "groupName" to myPrincipleGroup.groupName,
            "thumbnail" to targetThumbnail,
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