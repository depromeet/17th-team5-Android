package com.depromeet.team5.features.retrospect.annotation


sealed class TextFieldType {
    object Selling : TextFieldType()
    object Stock : TextFieldType()
    object Date : TextFieldType()
    object Return : TextFieldType()


    companion object {

        fun from(type: TextFieldType) = when (type) {
            Selling -> "selling"
            Stock -> "stock"
            Date -> "date"
            Return -> "return"
        }
    }
}
