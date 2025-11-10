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
