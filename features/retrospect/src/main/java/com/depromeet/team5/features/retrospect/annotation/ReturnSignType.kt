package com.depromeet.team5.features.retrospect.annotation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class ReturnSignType : Parcelable {
    object Plus : ReturnSignType(), Parcelable
    object Minus : ReturnSignType(), Parcelable


    companion object {

        fun from(type: ReturnSignType) = when (type) {
            is Plus -> "+"
            is Minus -> "-"
        }
    }
}
