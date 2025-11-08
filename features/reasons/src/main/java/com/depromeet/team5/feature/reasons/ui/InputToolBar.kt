package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.feature.reasons.R

@Composable
fun InputToolBar(
    hasImages: Boolean,
    hasLinks: Boolean,
    onClickAddImage: () -> Unit,
    onClickAddLink: () -> Unit,
    onClickAddMention: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_image),
            contentDescription = "image",
            tint = if (hasImages) HedgeColor.Text.Title else HedgeColor.Text.Assistive,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(
                    onClick = onClickAddImage,
                    interactionSource = remember { MutableInteractionSource() },
                )
                .padding(4.dp)
                .size(24.dp)
        )
        Icon(
            imageVector = HedgeIcon.Link,
            contentDescription = "link",
            tint = if (hasLinks) HedgeColor.Text.Title else HedgeColor.Text.Assistive,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(
                    onClick = onClickAddLink,
                    interactionSource = remember { MutableInteractionSource() },
                )
                .padding(4.dp)
                .size(24.dp)
        )
        Icon(
            painter = painterResource(R.drawable.ic_mention),
            contentDescription = "mention",
            tint = HedgeColor.Text.Assistive,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(
                    onClick = onClickAddMention,
                    interactionSource = remember { MutableInteractionSource() },
                )
                .padding(4.dp)
                .size(24.dp)
        )
    }
}

@Composable
fun InputToolBarIme(
    hasImages: Boolean,
    hasLinks: Boolean,
    onClickAddImage: () -> Unit,
    onClickAddLink: () -> Unit,
    onClickAddMention: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .background(color = HedgeColor.Neutral.BackgroundDefault),
    ) {
        HorizontalDivider(color = HedgeColor.Neutral.BackgroundSecondary)
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_image),
                contentDescription = "image",
                tint = if (hasImages) HedgeColor.Text.Title else HedgeColor.Text.Assistive,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = onClickAddImage,
                        interactionSource = remember { MutableInteractionSource() },
                    )
                    .padding(5.dp)
                    .size(20.dp)
            )
            Spacer(Modifier.size(4.dp))
            Icon(
                imageVector = HedgeIcon.Link,
                contentDescription = "link",
                tint = if (hasLinks) HedgeColor.Text.Title else HedgeColor.Text.Assistive,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = onClickAddLink,
                        interactionSource = remember { MutableInteractionSource() },
                    )
                    .padding(5.dp)
                    .size(20.dp)
            )
            Spacer(Modifier.size(4.dp))
            Icon(
                painter = painterResource(R.drawable.ic_mention),
                tint = HedgeColor.Text.Assistive,
                contentDescription = "mention",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = onClickAddMention,
                        interactionSource = remember { MutableInteractionSource() },
                    )
                    .padding(5.dp)
                    .size(20.dp)
            )
            Spacer(Modifier.weight(1f))
            HedgeButton.Text(
                text = stringResource(R.string.remain),
                imageVector = null,
                onClick = { keyboardController?.hide() }
            )
        }
    }
}