package com.depromeet.team5.core.domain.model

import androidx.compose.runtime.Immutable


@Immutable
data class MyPrincipleGroup(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val orderType: OrderType,
    val displayOrder: Int,
    val principles: List<MyPrinciple>
)

@Immutable
data class MyPrinciple(
    val id: Int,
    val groupId: Int,
    val principle: String,
    val description: String,
    val principleChecks: PrincipleChecks,
)

@Immutable
data class PrincipleChecks(
    val principleId: Int,
    val status: String,
    val reason: String,
    val imageUrls: List<String>,
    val links: List<String>,
) {
    companion object {
        fun createInit(principleId: Int) = PrincipleChecks(
            principleId = principleId,
            status = "UNSELECTED",
            reason = "",
            imageUrls = emptyList(),
            links = emptyList()
        )
    }
}