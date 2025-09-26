package com.depromeet.team5.core.domain.model

data class AnalysisEntity(
    val code: String,
    val text: String,
    val message: String
) {
    companion object {
        val EMPTY = AnalysisEntity(
            code = "",
            text = "",
            message = "",
        )
    }
}
