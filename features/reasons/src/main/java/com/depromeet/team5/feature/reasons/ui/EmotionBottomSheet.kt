package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.feature.reasons.Emotion
import com.depromeet.team5.feature.reasons.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionBottomSheet(
    showEmotionBottomSheet: Boolean,
    selected: Emotion,
    onClickCancel: () -> Unit,
    onClickDone: (Emotion) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = HedgeColor.Neutral.BackgroundDefault,
    dragHandle: @Composable (() -> Unit)? = null,
) {
    if (showEmotionBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            containerColor = containerColor,
            dragHandle = dragHandle,
            modifier = modifier
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Content(
                initialEmotion = selected,
                onClickCancel = onClickCancel,
                onClickDone = onClickDone,
            )
        }
    }
}

@Composable
private fun Content(
    initialEmotion: Emotion,
    onClickCancel: () -> Unit,
    onClickDone: (Emotion) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedEmotion by remember { mutableStateOf(initialEmotion) }
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 10.dp, top = 10.dp, bottom = 23.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.ask_emotion),
                color = HedgeColor.Text.Primary,
                style = HedgeTypography.Headline2.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(R.drawable.ic_close_circle),
                contentDescription = "close",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = onClickCancel,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )
        }
    }
    EmotionSlider(
        emotion = selectedEmotion,
        onValueChange = { selectedEmotion = it },
        modifier = Modifier
            .padding(start = 28.dp, end = 28.dp, bottom = 19.dp)
    )
    HedgeButton.CallToAction.Single(
        modifier = Modifier
            .fillMaxWidth(),
        text = stringResource(R.string.record),
        onClick = { onClickDone(selectedEmotion) },
    )
}

@Composable
@Preview(showBackground = true)
private fun EmotionBottomSheetContentPreview() {
    var selected by remember { mutableStateOf(Emotion.Neutral) }
    Column(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Content(
            initialEmotion = selected,
            onClickCancel = {},
            onClickDone = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun EmotionBottomSheetPreview() {
    var selected by remember { mutableStateOf(Emotion.Neutral) }

    EmotionBottomSheet(
        selected = selected,
        showEmotionBottomSheet = true,
        onClickCancel = {},
        onClickDone = {},
        onDismissRequest = {},
    )
}