package com.depromeet.team5.core.designsystem.foundation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

object HedgeShadow {

    val Regular = Shadow(
        radius = 20.dp,
        spread = 0.dp,
        color = Color(0xFF0D0F26).copy(alpha = 0.08f),
        offset = DpOffset(x = 0.dp, y = 6.dp)
    )

    val Medium = Shadow(
        radius = 60.dp,
        spread = 0.dp,
        color = Color(0xFF0D0F26).copy(alpha = 0.10f),
        offset = DpOffset(x = 0.dp, y = 12.dp)
    )

    val Strong = Shadow(
        radius = 30.dp,
        spread = 0.dp,
        color = Color(0xFF131A2B).copy(alpha = 0.32f),
        offset = DpOffset(x = 0.dp, y = 12.dp)
    )
}