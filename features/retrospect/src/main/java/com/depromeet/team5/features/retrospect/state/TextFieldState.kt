package com.depromeet.team5.features.retrospect.state


data class TextFieldState(
    val label: String,
    val text: String,
    val selection: Int,
    val isError: Boolean = false
) {
    companion object {
        val EMPTY = TextFieldState("", "", 0, false)
    }
}
