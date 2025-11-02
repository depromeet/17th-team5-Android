package com.depromeet.team5.core.designsystem.component

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.R
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import kotlinx.coroutines.delay

@Composable
fun HedgeToast(
    text: CharSequence,
    modifier: Modifier = Modifier,
    backgroundColor: Color = HedgeColor.Text.Secondary,
    textColor: Color = HedgeColor.Neutral.BackgroundSecondary,
    textStyle: TextStyle = HedgeTypography.Body3.Medium,
    icon: ImageVector? = null,
    iconTint: Color = Color.Unspecified,
    duration: Int = Toast.LENGTH_SHORT,
    topOffset: Dp = 52.dp,
    fadeInMs: Int = 800,
    fadeOutMs: Int = 800,
    onDismiss: (() -> Unit)? = null,
) {
    val visibleState = remember { MutableTransitionState(false) }
    val durationMillis = if (duration == Toast.LENGTH_SHORT) 2000L else 3500L

    LaunchedEffect(Unit) {
        visibleState.targetState = true
        delay(durationMillis)
        visibleState.targetState = false
    }

    LaunchedEffect(visibleState.currentState, visibleState.isIdle) {
        if (!visibleState.currentState && visibleState.isIdle) onDismiss?.invoke()
    }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(animationSpec = tween(fadeInMs)),
        exit = fadeOut(animationSpec = tween(fadeOutMs))
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = topOffset),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier
                    .heightIn(52.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(100)
                    )
                    .padding(start = 10.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = it.name,
                        tint = iconTint,
                    )
                }
                Spacer(
                    Modifier.then(
                        if (icon != null) Modifier.size(8.dp) else Modifier.size(10.dp)
                    )
                )
                if (text is AnnotatedString) {
                    Text(
                        text = text,
                        color = textColor,
                        style = textStyle,
                    )
                } else {
                    Text(
                        text = text.toString(),
                        color = textColor,
                        style = textStyle,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HedgeToastPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        HedgeToast(
            text = "매도 기록을 모두 입력해 주세요",
            icon = ImageVector.vectorResource(R.drawable.ic_toast_error),
            duration = Toast.LENGTH_SHORT
        )
    }
}