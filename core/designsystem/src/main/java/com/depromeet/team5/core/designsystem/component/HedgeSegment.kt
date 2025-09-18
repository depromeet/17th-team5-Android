package com.depromeet.team5.core.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

@Stable
interface HedgeSegmentColor {

    val track: Color
        @Composable get

    @Composable
    fun thumb(selected: Boolean): Color

    @Composable
    fun text(selected: Boolean): Color
}

@Stable
interface HedgeSegmentStyle {
    val trackShape: Shape
    val thumbShape: Shape
    val thumbPadding: PaddingValues
    val trackPadding: PaddingValues
    val thumbElevation: Dp
    val textStyle: TextStyle
}

object HedgeSegmentDefaults {
    @Composable
    fun color(
        track: Color = HedgeColor.GREY_OPACITY_200,
        thumbSelected: Color = HedgeColor.Neutral.BackgroundDefault,
        thumbUnselected: Color = Color.Transparent,
        selectedText: Color = HedgeColor.Text.Title,
        unselectedText: Color = HedgeColor.Text.Alternative,
    ) = object : HedgeSegmentColor {

        override val track: Color
            @Composable get() = track

        @Composable
        override fun thumb(selected: Boolean) =
            if (selected) thumbSelected else thumbUnselected

        @Composable
        override fun text(selected: Boolean) =
            if (selected) selectedText else unselectedText
    }

    @Composable
    fun style(
        trackShape: Shape = RoundedCornerShape(8.dp),
        thumbShape: Shape = RoundedCornerShape(6.dp),
        trackPadding: PaddingValues = PaddingValues(all = 3.dp),
        thumbPadding: PaddingValues = PaddingValues(horizontal = 7.dp, vertical = 6.dp),
        thumbElevation: Dp = 4.5.dp,
        textStyle: TextStyle = HedgeTypography.Caption1.Semibold,
    ) = object : HedgeSegmentStyle {
        override val trackShape = trackShape
        override val thumbShape = thumbShape
        override val trackPadding = trackPadding
        override val thumbPadding = thumbPadding
        override val thumbElevation = thumbElevation
        override val textStyle = textStyle
    }
}

@Composable
fun HedgeSegment(
    options: List<CharSequence>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: HedgeSegmentColor = HedgeSegmentDefaults.color(),
    style: HedgeSegmentStyle = HedgeSegmentDefaults.style(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    require(options.size >= 2)
    require(selectedIndex in options.indices)

    val density = LocalDensity.current
    var trackWidthPx by remember { mutableIntStateOf(0) }
    var trackHeightPx by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .background(color.track, style.trackShape)
            .padding(style.trackPadding)
            .onSizeChanged {
                trackWidthPx = it.width
                trackHeightPx = it.height
            },
        contentAlignment = Alignment.CenterStart
    ) {

        val segmentWidthPx = trackWidthPx / options.size.toFloat()
        val targetX = selectedIndex * segmentWidthPx
        val x by animateFloatAsState(targetValue = targetX, label = "thumbX")

        Box(
            modifier = Modifier
                .height(with(density) { trackHeightPx.toDp() })
                .width(with(density) { segmentWidthPx.toDp() })
                .graphicsLayer {
                    shape = style.thumbShape
                    clip = true
                    shadowElevation = style.thumbElevation.toPx()
                    ambientShadowColor = Color.Black.copy(alpha = 0.8f)
                    spotShadowColor = Color.Black.copy(alpha = 0.8f)
                    translationX = x
                }
                .background(color.thumb(true), style.thumbShape)
        )

        Row(
            modifier = Modifier.width(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .clickable(
                            enabled = enabled,
                            interactionSource = interactionSource,
                            indication = null
                        ) { if (index != selectedIndex) onSelectedIndexChange(index) }
                        .weight(1f)
                        .padding(style.thumbPadding),
                    contentAlignment = Alignment.Center
                ) {
                    if (text is AnnotatedString) {
                        Text(
                            text = text,
                            style = style.textStyle,
                            color = color.text(index == selectedIndex),
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = text.toString(),
                            style = style.textStyle,
                            color = color.text(index == selectedIndex),
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun HedgeSegmentPreview() {
    var idx by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .background(color = HedgeColor.Neutral.BackgroundDefault)
            .padding(horizontal = 20.dp, vertical = 120.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HedgeSegment(
            options = listOf("Label", "Label"),
            selectedIndex = idx,
            onSelectedIndexChange = { idx = it }
        )
        HedgeSegment(
            options = listOf("원", "$"),
            selectedIndex = idx,
            onSelectedIndexChange = { idx = it },
        )
        HedgeSegment(
            options = listOf("Longgggggggggg", "Short"),
            selectedIndex = idx,
            onSelectedIndexChange = { idx = it },
        )
    }
}