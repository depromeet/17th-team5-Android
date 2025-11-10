package com.depromeet.team5.core.domain.model


enum class OrderType {
    BUY, SELL, NONE;

    fun toKorean(): String = when (this) {
        BUY -> "매수"
        SELL -> "매도"
        NONE -> ""
    }

    fun toPrincipleType(): PrincipleType = when (this) {
        BUY -> PrincipleType.BUY
        SELL -> PrincipleType.SELL
        else -> error("잘못된 OrderType이 들어왔습니다. Params : { $this }")
    }
}

fun String.toOrderType(): OrderType = when (this.uppercase()) {
    "매수", "BUY" -> OrderType.BUY
    "매도", "SELL" -> OrderType.SELL
    else -> OrderType.NONE
}