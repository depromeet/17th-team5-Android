package com.depromeet.team5.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

/*
* todo migrate to ui module
* */
@Composable
private fun HedgeTopBarBackDefault() {
    Icon(
        imageVector = HedgeIcon.ArrowLeftThick,
        contentDescription = "back",
        tint = HedgeColor.Text.Primary
    )
}

private fun Modifier.topBarBackground(color: Color?): Modifier =
    if (color == null) this else this.then(Modifier.background(color))

@Composable
fun HedgeTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    back: @Composable () -> Unit = { HedgeTopBarBackDefault() },
    title: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    onClickBack: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .topBarBackground(backgroundColor)
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
            .height(44.dp)
            .padding(start = 4.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(vertical = 2.dp)
                .clip(CircleShape)
                .clickable(
                    interactionSource = interaction,
                    indication = ripple(),
                    onClick = onClickBack
                ),
            contentAlignment = Alignment.Center
        ) {
            back()
        }

        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
            title?.invoke()
        }
        action?.invoke()
    }
}

@Composable
@Preview
private fun HedgeTopBarPreview() {
    Column {
        HedgeTopBar(
            onClickBack = { },
            backgroundColor = HedgeColor.Neutral.BackgroundDefault,
            title = {
                Text(
                    text = "제목",
                    style = HedgeTypography.Headline2.SemiBold,
                )
            },
            action = {
                HedgeButton.Text(
                    text = "다음",
                    imageVector = null,
                    onClick = { },
                    size = HedgeButton.Text.Size.Large,
                    color = HedgeButton.Text.Color.Primary,
                )
            }
        )
    }
}