package com.depromeet.team5.core.domain.model


enum class OrderType {
    BUY, SELL, NONE;

    fun toKorean(): String = when (this) {
        BUY -> "매수"
        SELL -> "매도"
        NONE -> ""
    }
}
