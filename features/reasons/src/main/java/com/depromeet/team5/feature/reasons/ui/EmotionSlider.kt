package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.feature.reasons.Emotion
import com.depromeet.team5.feature.reasons.R
import kotlin.math.roundToInt

/*
* todo
*  thumb shadow
*  refactoring
*  slider 반영 onClickDone에 잘 적용시키기
* */
@Composable
fun EmotionSlider(
    emotion: Emotion,
    onValueChange: (Emotion) -> Unit,
    modifier: Modifier = Modifier,
) {
    val emotions = Emotion.entries
    val steps = emotions.lastIndex
    val density = LocalDensity.current

    var slider by remember { mutableFloatStateOf(emotions.indexOf(emotion).toFloat()) }

    var pos by remember { mutableFloatStateOf(emotions.indexOf(emotion).toFloat()) }

    var isDragging by remember { mutableStateOf(false) }

    LaunchedEffect(emotion) {
        if (isDragging) return@LaunchedEffect // ★ FIX-JUMP
        val idx = emotions.indexOf(emotion).coerceIn(0, steps)
        pos = idx.toFloat()
        slider = idx.toFloat()
    }

    val dotOffsets = remember { List(emotions.size) { mutableStateOf(Offset.Zero) } }

    fun lerp(a: Float, b: Float, t: Float) = a + (b - a) * t

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val trackW = constraints.maxWidth.toFloat()
        val trackH = with(density) { 32.dp.toPx() }
        val thumbR = trackH / 2f

        val selectedIndex = slider.roundToInt()

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            emotions.forEachIndexed { index, emotion ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val selected = index == slider.roundToInt()
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .clickable {
                                slider = index.toFloat()
                                onValueChange(emotion)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                if (selected) emotion.enabledIconRes else emotion.disabledIconRes
                            ),
                            contentDescription = stringResource(emotion.labelRes),
                        )
                    }
                    Spacer(Modifier.size(31.dp))
                    val dotColor = HedgeColor.Text.Disabled
                    Spacer(
                        modifier = Modifier
                            .size(6.dp)
                            .drawBehind {
                                drawCircle(dotColor)
                            }
                            .onGloballyPositioned {
                                dotOffsets[index].value = Offset(
                                    it.boundsInRoot().left + it.size.width / 2,
                                    it.boundsInParent().top + it.size.height / 2
                                )
                            }
                    )
                    // 스펙은 23.dp인데 피그마와 달리 너무 좁아보여 자체 조정
                    Spacer(Modifier.size(25.dp))
                    Text(
                        text = stringResource(emotion.labelRes),
                        color = if (selected) HedgeColor.Text.Primary else HedgeColor.Text.Alternative,
                        style = if (selected) HedgeTypography.Caption1.Semibold else HedgeTypography.Caption1.Medium
                    )
                }
            }
        }

        val dotCenterInRoot = dotOffsets[selectedIndex]
        val iconWidthPx = with(density) { 32.dp.toPx() }
        val iconHeightPx = with(density) { 32.dp.toPx() }

        val trackPaddingPx = with(density) { 8.5.dp.toPx() }
        val trackWidthPx = trackW - trackPaddingPx * 2f
        val t = if (steps == 0) 0f else (pos / steps).coerceIn(0f, 1f)
        val thumbCxPx = lerp(
            trackPaddingPx + thumbR,
            trackPaddingPx + trackWidthPx - thumbR,
            t
        )

        fun setPosFromX(xInTrackPx: Float) {
            val usableW = trackWidthPx - thumbR * 2f
            val clamped = (xInTrackPx - (trackPaddingPx + thumbR)).coerceIn(0f, usableW)
            val newT = if (usableW <= 0f) 0f else clamped / usableW
            pos = (newT * steps).coerceIn(0f, steps.toFloat())
        }

        fun snapToNearest() {
            val nearest = pos.roundToInt().coerceIn(0, steps)
            pos = nearest.toFloat()
            if (slider != nearest.toFloat()) {
                slider = nearest.toFloat()
            }
            onValueChange(emotions[nearest])
        }

        Box(
            modifier = Modifier
                .offset {
                    val localY = dotCenterInRoot.value.y - iconHeightPx / 2f
                    IntOffset(0, localY.roundToInt())
                }
                .pointerInput(steps, trackWidthPx, thumbR) {
                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        isDragging = true
                        setPosFromX(down.position.x)

                        horizontalDrag(down.id) { change ->
                            val usableW = trackWidthPx - thumbR * 2f
                            val deltaT = if (usableW <= 0f) 0f else (change.positionChange().x / usableW)
                            pos = (pos + deltaT * steps).coerceIn(0f, steps.toFloat())
                            change.consume()

                            val nearest = pos.roundToInt().coerceIn(0, steps)
                            if (slider != nearest.toFloat()) {
                                slider = nearest.toFloat()
                            }
                        }
                        snapToNearest()
                        isDragging = false
                    }
                }
        ) {
            val trackColor = HedgeColor.Text.Disabled.copy(0.6f)
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = (8.5).dp)
                    .height(32.dp)
                    .zIndex(-1f)
            ) {
                val r = size.height / 2f
                val cy = size.height / 2f
                val baseRect = Rect(0f, cy - r, size.width, cy + r)

                drawRoundRect(
                    color = trackColor,
                    topLeft = Offset(baseRect.left, baseRect.top),
                    size = baseRect.size,
                    cornerRadius = CornerRadius(r, r)
                )

                val tCanvas = if (steps == 0) 0f else (pos / steps).coerceIn(0f, 1f)
                val thumbCx = lerp(r, size.width - r, tCanvas)
                val activeW = thumbCx

                if (activeW > 0f) {
                    val activeRect = Rect(0f, cy - r, activeW, cy + r)
                    val path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = activeRect,
                                topLeft = CornerRadius(r, r),
                                bottomLeft = CornerRadius(r, r),
                            )
                        )
                    }
                    drawPath(
                        path = path,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF12B886),
                                Color(0xFF10AA7B)
                            ),
                            startX = 0f,
                            endX = activeW
                        )
                    )
                }
            }

            Icon(
                painter = painterResource(R.drawable.ic_thumb),
                contentDescription = "thumb",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(with(density) { trackH.toDp() })
                    .offset {
                        IntOffset((thumbCxPx - iconWidthPx / 2f).roundToInt(), 0)
                    }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 300)
@Composable
fun EmotionSliderPreview() {
    var selected by remember { mutableStateOf(Emotion.Neutral) }

    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        EmotionSlider(
            emotion = selected,
            onValueChange = { selected = it }
        )
        Spacer(Modifier.size(40.dp))
    }
}
