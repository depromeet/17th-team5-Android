package com.depromeet.team5.features.retrospect.annotation


sealed class ReturnSignType {
    object Plus : ReturnSignType()
    object Minus : ReturnSignType()


    companion object {

        fun from(type: ReturnSignType) = when (type) {
            is Plus -> "+"
            is Minus -> "-"
        }
    }
}
