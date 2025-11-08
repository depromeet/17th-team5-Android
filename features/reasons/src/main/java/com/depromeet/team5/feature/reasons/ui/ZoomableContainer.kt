package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.ui.extensions.toPx
import kotlin.math.abs

@Composable
fun ZoomableContainer(
    modifier: Modifier = Modifier,
    onSwipe: (Int) -> Unit = {},
    image: @Composable (Modifier) -> Unit,
) {
    val configuration = LocalConfiguration.current
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    val touchSlop = 8.dp.toPx()
    val screenWidth = configuration.screenWidthDp.dp.toPx()
    val screenHeight = configuration.screenHeightDp.dp.toPx()

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceAtLeast(1f)

                    val maxX = (screenWidth * scale - screenWidth) / 2
                    val maxY = (screenHeight * scale - screenHeight) / 2

                    offset = Offset(
                        x = (offset.x + pan.x).coerceIn(-maxX, maxX),
                        y = (offset.y + pan.y).coerceIn(-maxY, maxY)
                    )

                    if (abs(pan.x) > touchSlop && scale <= 1f) {
                        onSwipe(if (pan.x > 0) -1 else 1)
                    }
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        image(
            Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
        )
    }
}

@Preview
@Composable
private fun ZoomableImagePreview() {
    ZoomableContainer { modifier ->
        Image(
            painter = painterResource(com.depromeet.team5.core.ui.R.drawable.img_badge_platinum),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = modifier
                .fillMaxSize()
        )
    }
}