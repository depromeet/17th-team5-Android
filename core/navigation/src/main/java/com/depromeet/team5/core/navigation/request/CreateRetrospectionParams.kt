package com.depromeet.team5.core.navigation.request

import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.model.OrderType
import kotlinx.serialization.Serializable


data class CreateRetrospectionParams(
    val symbol: String,
    val companyName: String,
    val market: String,
    val currency: String,
    val orderDate: String,
    val orderType: OrderType,
    val price: Int,
    val volume: Int,
    val returnRate: Double?,
    val content: String?,
    val principles: List<MyPrinciple>,
    val principleChecks: List<PrincipleCheckParams>?,
    val emotion: EmotionParams?,
) {

    companion object {

        val EMPTY = CreateRetrospectionParams(
            symbol = "",
            companyName = "",
            volume = 0,
            market = "",
            currency = "",
            orderDate = "",
            orderType = OrderType.BUY,
            price = 0,
            emotion = null,
            content = null,
            principles = emptyList(),
            principleChecks = null,
            returnRate = null
        )
    }
}

@Serializable
data class PrincipleCheckParams(
    val isFollowed: Boolean,
    val principleId: Int
)

enum class EmotionParams {
    ANXIETY, IMPULSE, MINDLESSNESS, CONFIDENCE, CONVICTION
}
