package com.depromeet.team5.core.navigation.request.model

import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.PrincipleState

data class PrincipleGroupState(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val orderType: OrderType,
    val displayOrder: Int,
    val principles: List<PrincipleState>
)