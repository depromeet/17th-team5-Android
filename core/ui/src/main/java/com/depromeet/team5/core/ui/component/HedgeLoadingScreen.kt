package com.depromeet.team5.core.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HedgeLoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ){
        HedgeSpinner(modifier = modifier)
    }
}

@Composable
private fun HedgeSpinner(
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "spin")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )

    Canvas(
        modifier = modifier.size(32.dp)
    ) {
        val stroke = Stroke(width = 4.dp.toPx().toFloat(), cap = StrokeCap.Round)
        val diameter = size.minDimension.toFloat()
        val inset = stroke.width / 2f
        val arcSize = Size(diameter - stroke.width, diameter - stroke.width)

        rotate(degrees = angle) {
            drawArc(
                color = Color(0xFF0EBC80),
                startAngle = 270f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
                size = arcSize,
                style = stroke
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HedgeLoadingScreenPreview() {
    HedgeLoadingScreen()
}
