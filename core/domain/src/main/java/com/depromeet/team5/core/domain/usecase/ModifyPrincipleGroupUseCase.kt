package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class ModifyPrincipleGroupUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    operator fun invoke(
        groupId: Int,
        groupName: String,
        thumbnail: String
    ) = hedgeRepository.modifyPrincipleGroup(
        groupId = groupId,
        body = mapOf(
            "groupName" to groupName,
            "thumbnail" to thumbnail
        )
    )
}
