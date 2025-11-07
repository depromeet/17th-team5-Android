package com.depromeet.team5.core.domain.request

import com.depromeet.team5.core.domain.model.OrderType

data class CreateRetrospectionRequest(
    val symbol: String,
    val companyName: String,
    val market: String,
    val currency: String,
    val orderDate: String,
    val orderType: OrderType,
    val price: Int,
    val volume: Int,
    val returnRate: Double?,
    val principleChecks: List<PrincipleCheckRequest>,
) {

    companion object {

        val EMPTY = CreateRetrospectionRequest(
            symbol = "",
            companyName = "",
            volume = 0,
            market = "",
            currency = "",
            orderDate = "",
            orderType = OrderType.BUY,
            price = 0,
            principleChecks = emptyList(),
            returnRate = null
        )
    }
}

data class PrincipleCheckRequest(
    val principleId: Int,
    val status: String,
    val reason: String,
    val imageUrls: List<Int>,
    val links: List<String>,
)