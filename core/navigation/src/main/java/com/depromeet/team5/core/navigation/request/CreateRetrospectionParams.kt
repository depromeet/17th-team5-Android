package com.depromeet.team5.core.navigation.request

import kotlinx.serialization.Serializable


@Serializable
data class CreateRetrospectionParams(
    val symbol: String,
    val companyName: String,
    val market: String,
    val currency: String,
    val orderDate: String,
    val orderType: OrderTypeParams,
    val price: Int,
    val volume: Int,
    val returnRate: Double?,
    val content: String?,
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
            orderType = OrderTypeParams.BUY,
            price = 0,
            emotion = null,
            content = null,
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

@Serializable
enum class EmotionParams {
    ANXIETY, IMPULSE, MINDLESSNESS, CONFIDENCE, CONVICTION
}

@Serializable
enum class OrderTypeParams {
    BUY, SELL;

    fun toKorean(): String = when (this) {
        BUY -> "매수"
        SELL -> "매도"
    }
}
