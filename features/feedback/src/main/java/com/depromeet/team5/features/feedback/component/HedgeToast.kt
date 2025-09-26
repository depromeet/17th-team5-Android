package com.depromeet.team5.features.feedback.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


@Composable
fun HedgeToast(
    state: HedgeToastState,
    message: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(state.isShow) {
        if (state.isShow) {
            delay(state.duration)
            state.dismiss()
        }
    }

    val visibleState = remember { MutableTransitionState(false) }
        .apply { targetState = state.isShow }


    if (visibleState.currentState || visibleState.targetState) {
        Popup(
            alignment = Alignment.TopCenter
        ) {
            AnimatedVisibility(
                visibleState = visibleState,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = modifier
                        .shadow(
                            elevation = 30.dp,
                            shape = RoundedCornerShape(61.dp),
                            spotColor = Color(0x52131A2B),
                            ambientColor = Color(0x52131A2B)
                        )
                        .background(HedgeColor.Text.Secondary, shape = RoundedCornerShape(61.dp)),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, end = 20.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        icon()
                        Spacer(modifier = Modifier.size(8.dp))
                        message()
                    }
                }
            }
        }
    }
}

@Composable
fun rememberHedgeToastState(
    duration: Duration = 5.seconds
): HedgeToastState = remember(duration) {
    HedgeToastState(
        initialIsShow = false,
        duration = duration
    )
}

@Stable
class HedgeToastState(
    initialIsShow: Boolean,
    val duration: Duration
) {

    var isShow by mutableStateOf(initialIsShow)
        private set

    fun show() {
        isShow = true
    }

    fun dismiss() {
        isShow = false
    }
}


@Preview
@Composable
private fun HedgeToastPreView() {
    val state = rememberHedgeToastState()

    LaunchedEffect(Unit) {
        delay(3000)
        state.show()
    }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .background(HedgeColor.BLUE_500)

        ) {
            HedgeToast(
                state = state,
                message = {
                    Text(
                        text = "회고가 삭제되었습니다",
                        style = HedgeTypography.Body3.Medium,
                        color = HedgeColor.Neutral.BackgroundSecondary
                    )
                },
                icon = {
                    Image(imageVector = HedgeIcon.Check, contentDescription = null)
                }
            )
        }
    }
}