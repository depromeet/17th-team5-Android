package com.depromeet.team5.features.retrospect.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextFieldState(
    val label: String,
    val text: String,
    val selection: Int,
    val isError: Boolean = false
) : Parcelable {
    companion object {
        val EMPTY = TextFieldState("", "", 0, false)
    }
}
