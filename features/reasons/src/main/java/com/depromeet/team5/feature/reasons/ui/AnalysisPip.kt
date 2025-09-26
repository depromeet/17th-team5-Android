package com.depromeet.team5.feature.reasons.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.feature.reasons.R

/*
* todo
* top scroll area fade
* gradient background
* */
@Composable
fun AnalysisPip(
    showAnalysisPip: Boolean,
    analysisReport: String?,
    onDismissRequest: () -> Unit,
    onClickCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (showAnalysisPip) {
        BackHandler(enabled = true) { onDismissRequest() }
        val density = LocalDensity.current
        val imeVisible = WindowInsets.ime.getBottom(density) > 0

        var pipSize by remember { mutableStateOf(IntSize.Zero) }
        var baseTopPx by remember { mutableIntStateOf(0) }
        var parentSize by remember { mutableStateOf(IntSize.Zero) }
        var dragOffset by remember { mutableStateOf(IntOffset.Zero) }
        val targetY = remember(imeVisible, baseTopPx) {
            if (imeVisible) -baseTopPx else 0
        }

        val animatedY by animateIntAsState(
            targetValue = targetY,
            label = "pip-y"
        )

        LaunchedEffect(imeVisible) {
            dragOffset = IntOffset.Zero
        }

        Box(
            modifier = modifier
                .zIndex(1f)
                .padding(bottom = 16.dp)
                .onSizeChanged { pipSize = it }
                .onGloballyPositioned {
                    if (!imeVisible && dragOffset == IntOffset.Zero) {
                        baseTopPx = it.positionInParent().y.toInt()
                    }
                    it.parentLayoutCoordinates?.size?.let { parentSize = it }
                }
                .offset { IntOffset(x = 0, y = animatedY + dragOffset.y) }
                .pointerInput(imeVisible, pipSize, parentSize, animatedY) {
                    detectDragGestures(
                        onDrag = { change, drag ->
                            change.consume()

                            val topAtZero = baseTopPx + animatedY
                            val maxTop = (parentSize.height - pipSize.height).coerceAtLeast(0)

                            val minDy = 0 - topAtZero
                            val maxDy = maxTop - topAtZero

                            val newY = (dragOffset.y + drag.y.toInt()).coerceIn(minDy, maxDy)
                            dragOffset = IntOffset(x = 0, y = newY)
                        }
                    )
                }
                .background(color = Color(0xFF0D1119), shape = RoundedCornerShape(30.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 26.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color(0xFF7A56EF).copy(alpha = 0.32f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_twinkle),
                            contentDescription = null,
                            tint = Color(0xFF00FFDD),
                        )
                    }
                    Spacer(Modifier.size(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.market_condition),
                            style = HedgeTypography.Body3.SemiBold,
                            color = HedgeColor.Text.White
                        )
                        // todo 실제 데이터
                        Text(
                            text = "8월 25일 분석",
                            style = HedgeTypography.Caption1.Regular,
                            color = HedgeColor.Text.Assistive
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(bottom = 2.dp)
                            .clickable(
                                onClick = onClickCancel,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                            .border(
                                width = 1.dp,
                                color = HedgeColor.Neutral.BackgroundDefault.copy(0.08f),
                                shape = CircleShape
                            )
                            .size(28.dp)
                            .background(
                                color = HedgeColor.Neutral.BackgroundDefault.copy(0.08f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close_small),
                            contentDescription = null,
                            tint = HedgeColor.Text.Assistive,
                        )
                    }
                }
                analysisReport?.let {
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .padding(top = 18.dp)
                            .height(120.dp)
                            .verticalScroll(scrollState)
                    ) {
                        Text(
                            text = it,
                            color = HedgeColor.Text.Disabled,
                            style = HedgeTypography.Label1.Medium,
                        )
                    }
                } ?: AnalysisLoadingProgress(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(138.dp)
                )
            }
        }
    }
}

@Composable
private fun AnalysisLoadingProgress(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            strokeWidth = 2.5.dp,
            color = HedgeColor.Text.Disabled,
            strokeCap = StrokeCap.Round,
            trackColor = HedgeColor.Text.Disabled.copy(alpha = 0.1f),
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(R.string.loading_analyze),
            style = HedgeTypography.Label1.SemiBold,
            color = HedgeColor.Text.Disabled
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PipModalPreview() {
    var show by remember { mutableStateOf(true) }

    Box(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .onSizeChanged { }
    ) {
        AnalysisPip(
            showAnalysisPip = show,
            analysisReport = "",
            onDismissRequest = { show = false },
            onClickCancel = { show = false },
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}