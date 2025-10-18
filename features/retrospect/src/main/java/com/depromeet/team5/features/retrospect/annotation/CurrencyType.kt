package com.depromeet.team5.features.retrospect.annotation


sealed class CurrencyType {
    object KRW : CurrencyType()
    object USD : CurrencyType()
    object EUR : CurrencyType()
    object JPY : CurrencyType()


    companion object {

        fun from(type: CurrencyType) = when (type) {
            is KRW -> "KRW"
            is USD -> "USD"
            is EUR -> "EUR"
            is JPY -> "JPY"
        }
    }
}
