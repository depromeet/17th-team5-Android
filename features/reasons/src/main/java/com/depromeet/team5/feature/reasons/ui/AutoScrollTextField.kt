package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.feature.reasons.R

@Composable
fun AutoScrollTextField(
    content: TextFieldValue,
    isImeVisible: Boolean,
    focusRequester: FocusRequester,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    var lastLayout by remember { mutableStateOf<TextLayoutResult?>(null) }
    var isFocused by remember { mutableStateOf(false) }
    val viewRequester = remember { BringIntoViewRequester() }

    // 1.타이핑 중 줄바꿈 자동 스크롤
    LaunchedEffect(content.selection, isFocused) {
        if (!isFocused) return@LaunchedEffect
        val layout = lastLayout ?: return@LaunchedEffect
        val end = content.selection.end.coerceIn(0, layout.layoutInput.text.length)
        val cursor = layout.getCursorRect(end).inflate(8f)
        viewRequester.bringIntoView(cursor)
    }

    // 2.ime 올라올 때
    val ime = WindowInsets.ime
    var prevImeVisible by remember { mutableStateOf(isImeVisible) }

    val stepPx = remember(density) { with(density) { 24.dp.roundToPx() } }
    val minFramesBetween = 2
    val stableFramesNeeded = 2
    val maxWaitMs = 2000L

    LaunchedEffect(isImeVisible, isFocused) {
        val turnedOn = !prevImeVisible && isImeVisible
        prevImeVisible = isImeVisible
        if (!isFocused || !turnedOn) return@LaunchedEffect

        val layout = lastLayout ?: return@LaunchedEffect
        val end = content.selection.end.coerceIn(0, layout.layoutInput.text.length)
        val rect = layout.getCursorRect(end).inflate(8f)
        bringOnImeRise(
            rectPx = rect,
            requester = viewRequester,
            density = density,
            ime = ime,
            isImeVisible = isImeVisible,
            stepPx = stepPx,
            minFramesBetween = minFramesBetween,
            stableFramesNeeded = stableFramesNeeded,
            maxWaitMs = maxWaitMs
        )
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
    ) {
        val maxHeight = maxHeight
        BasicTextField(
            value = content,
            onValueChange = onValueChange,
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .heightIn(maxHeight)
                .onFocusChanged { isFocused = it.isFocused }
                .bringIntoViewRequester(viewRequester),
            textStyle = HedgeTypography.Body3.Regular.copy(
                color = HedgeColor.Text.Title,
            ),
            cursorBrush = SolidColor(HedgeColor.Brand.Primary),
            onTextLayout = { lastLayout = it },
        ) { innerTextField ->
            if (content.text.isEmpty()) {
                Text(
                    text = stringResource(R.string.write_reason_placeholder),
                    color = HedgeColor.Text.Assistive,
                    style = HedgeTypography.Body3.Medium,
                    modifier = Modifier
                )
            }
            innerTextField()
        }
    }
}

private suspend fun bringOnImeRise(
    rectPx: Rect,
    requester: BringIntoViewRequester,
    ime: WindowInsets,
    isImeVisible: Boolean,
    density: Density,
    stepPx: Int,
    minFramesBetween: Int,
    stableFramesNeeded: Int,
    maxWaitMs: Long
) {

    // 1. 즉시 1회
    withFrameNanos { }
    requester.bringIntoView(rectPx)

    // 2. 진행 중 보정
    var startNs = 0L
    var framesSinceEmit = 0
    var lastBottom = ime.getBottom(density)
    var stable = 0
    var accRise = 0

    withFrameNanos { now -> startNs = now }

    while (isImeVisible) {
        val now = withFrameNanos { it }
        framesSinceEmit++

        val cur = ime.getBottom(density)
        val delta = cur - lastBottom
        lastBottom = cur

        if (delta > 1) accRise += delta

        if (accRise >= stepPx && framesSinceEmit >= minFramesBetween) {
            requester.bringIntoView(rectPx)
            accRise = 0
            framesSinceEmit = 0
        }

        if (kotlin.math.abs(delta) <= 1) stable++ else stable = 0
        val elapsedMs = (now - startNs) / 1_000_000L
        if (stable >= stableFramesNeeded || elapsedMs >= maxWaitMs) break
    }

    // 3. 최종 보정
    requester.bringIntoView(rectPx)
}
