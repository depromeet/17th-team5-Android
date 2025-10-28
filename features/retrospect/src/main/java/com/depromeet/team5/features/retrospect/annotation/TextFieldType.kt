package com.depromeet.team5.features.retrospect.annotation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class TextFieldType : Parcelable {
    object Selling : TextFieldType(), Parcelable
    object Stock : TextFieldType(), Parcelable
    object Date : TextFieldType(), Parcelable
    object Return : TextFieldType(), Parcelable


    companion object {

        fun from(type: TextFieldType) = when (type) {
            Selling -> "selling"
            Stock -> "stock"
            Date -> "date"
            Return -> "return"
        }
    }
}
