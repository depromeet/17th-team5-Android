package com.depromeet.team5.core.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Retrospection(
    val id: Int,
    val userId: Int,
    val market: String,
    val companyName: String,
    val companyLogo: String,
    val price: Int,
    val createdAt: String,
    val currency: String,
    val orderDate: String,
    val orderType: OrderType,
    val returnRate: Double,
    val badge: String,
    val symbol: String,
    val updatedAt: String,
    val volume: Int,
    val principleGroupState: PrincipleGroupState?,
    val memos: List<Memo>
)