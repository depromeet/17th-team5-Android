package com.depromeet.team5.core.domain.model

data class Analysis(
    val code: String,
    val text: String,
    val message: String
) {
    companion object {
        val EMPTY = Analysis(
            code = "",
            text = "",
            message = "",
        )
    }
}
