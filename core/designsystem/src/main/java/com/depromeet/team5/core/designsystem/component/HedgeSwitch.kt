package com.depromeet.team5.core.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor

@Stable
interface HedgeSwitchColor {
    @Composable
    fun trackColor(checked: Boolean): Color

    @Composable
    fun thumbColor(checked: Boolean): Color
}

@Stable
interface HedgeSwitchStyle {
    val trackWidth: Dp
    val trackHeight: Dp
    val trackPadding: Dp
    val thumbSize: Dp
    val trackShape: Shape
    val thumbShape: Shape
}

@Stable
private class DefaultHedgeSwitchColor(
    private val checkedTrack: Color,
    private val uncheckedTrack: Color,
    private val checkedThumb: Color,
    private val uncheckedThumb: Color,
) : HedgeSwitchColor {
    @Composable
    override fun trackColor(checked: Boolean) =
        if (checked) checkedTrack else uncheckedTrack

    @Composable
    override fun thumbColor(checked: Boolean) =
        if (checked) checkedThumb else uncheckedThumb
}

object HedgeSwitchDefaults {

    @Composable
    fun style(
        trackWidth: Dp = 45.dp,
        trackHeight: Dp = 28.dp,
        trackPadding: Dp = 3.dp,
        thumbSize: Dp = 22.dp,
        trackShape: Shape = CircleShape,
        thumbShape: Shape = CircleShape,
    ) = object : HedgeSwitchStyle {
        override val trackWidth = trackWidth
        override val trackHeight = trackHeight
        override val trackPadding = trackPadding
        override val thumbSize = thumbSize
        override val trackShape = trackShape
        override val thumbShape = thumbShape
    }

    @Composable
    fun color(
        checkedTrack: Color = HedgeColor.Brand.Primary,
        uncheckedTrack: Color = HedgeColor.GREY_OPACITY_300,
        checkedThumb: Color = HedgeColor.Neutral.BackgroundDefault,
        uncheckedThumb: Color = HedgeColor.Neutral.BackgroundDefault,
    ) = object : HedgeSwitchColor {

        @Composable
        override fun trackColor(checked: Boolean) =
            if (checked) checkedTrack else uncheckedTrack

        @Composable
        override fun thumbColor(checked: Boolean) =
            if (checked) checkedThumb else uncheckedThumb
    }
}

@Composable
fun HedgeSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: HedgeSwitchColor = HedgeSwitchDefaults.color(),
    style: HedgeSwitchStyle = HedgeSwitchDefaults.style(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val density = LocalDensity.current

    val trackColor by animateColorAsState(
        color.trackColor(checked),
        label = "trackColor"
    )

    val thumbOffset by animateDpAsState(
        if (checked) style.trackWidth - style.thumbSize - style.trackPadding else style.trackPadding,
        label = "thumbOffset"
    )
    val thumbOffsetPx = with(density) { thumbOffset.toPx() }

    Box(
        modifier = modifier
            .width(style.trackWidth)
            .height(style.trackHeight)
            .background(
                color = trackColor,
                shape = style.trackShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onCheckedChange(!checked) }
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(x = thumbOffsetPx.toInt(), y = 0) }
                .size(style.thumbSize)
                .background(
                    color = color.thumbColor(checked),
                    shape = style.thumbShape
                )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SwitchPreview() {
    Column(
        modifier = Modifier.padding(100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        var checked by remember { mutableStateOf(false) }
        HedgeSwitch(
            checked = checked,
            onCheckedChange = { checked = it },
        )
        HedgeSwitch(
            checked = checked.not(),
            onCheckedChange = { checked = it },
        )
    }
}