package com.depromeet.team5.core.designsystem.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

@Composable
@ReadOnlyComposable
fun Color(
    lightMode: Color,
    darkMode: Color,
): Color = if (isSystemInDarkTheme()) {
    darkMode
} else {
    lightMode
}
