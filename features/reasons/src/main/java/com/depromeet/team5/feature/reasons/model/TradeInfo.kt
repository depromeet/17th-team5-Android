package com.depromeet.team5.feature.reasons.model

import androidx.compose.runtime.Immutable
import com.depromeet.team5.core.domain.model.OrderType

@Immutable
data class TradeInfo(
    val logoUri: String? = null,
    val stockName: String,
    val orderType: OrderType,
    val price: Long,
    val currency: String,
    val volume: Int,
    val orderDate: String,
)