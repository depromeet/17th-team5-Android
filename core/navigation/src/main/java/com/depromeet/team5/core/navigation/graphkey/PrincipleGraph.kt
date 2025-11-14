package com.depromeet.team5.core.navigation.graphkey

import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.navigation.Path
import kotlinx.serialization.Serializable


@Serializable
data class PrincipleGraph(
    val groupId: Int,
    val path: Path,
    val orderType: OrderType
)
