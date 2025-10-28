package com.depromeet.team5.features.retrospect.annotation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class CurrencyType : Parcelable {

    object KRW : CurrencyType(), Parcelable
    object USD : CurrencyType(), Parcelable
    object EUR : CurrencyType(), Parcelable
    object JPY : CurrencyType(), Parcelable


    companion object {

        fun from(type: CurrencyType) = when (type) {
            is KRW -> "KRW"
            is USD -> "USD"
            is EUR -> "EUR"
            is JPY -> "JPY"
        }
    }
}
