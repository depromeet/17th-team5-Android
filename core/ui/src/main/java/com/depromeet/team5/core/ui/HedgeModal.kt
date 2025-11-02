package com.depromeet.team5.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

@Composable
fun HedgeModal(
    showModal: Boolean,
    icon: Painter? = rememberVectorPainter(HedgeIcon.Empty),
    title: CharSequence,
    description: CharSequence?,
    submitButton: Pair<CharSequence, () -> Unit>,
    cancelButton: Pair<CharSequence, () -> Unit>?,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    dialogProperties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
) {
    if (showModal) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = dialogProperties,
        ) {

            val minHeight = when {
                icon == null && description == null -> 126.dp
                icon == null || description == null -> 150.dp
                else -> 188.dp
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .heightIn(min = minHeight)
                    .background(
                        HedgeColor.Neutral.BackgroundDefault,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                icon?.let {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(Modifier.size(8.dp))
                }
                if (title is AnnotatedString) {
                    Text(
                        text = title,
                        style = HedgeTypography.Body1.SemiBold,
                        color = HedgeColor.Text.Title,
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                    )
                } else {
                    Text(
                        text = title.toString(),
                        style = HedgeTypography.Body1.SemiBold,
                        color = HedgeColor.Text.Title,
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                    )
                }
                Spacer(Modifier.size(2.dp))
                description?.let {
                    if (it is AnnotatedString) {
                        Text(
                            text = it,
                            style = HedgeTypography.Body3.Medium,
                            color = HedgeColor.Text.Secondary,
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                        )
                    } else {
                        Text(
                            text = it.toString(),
                            style = HedgeTypography.Body3.Medium,
                            color = HedgeColor.Text.Secondary,
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                        )
                    }
                }
                Spacer(Modifier.size(20.dp))
                cancelButton?.let { (text, onClickCancel) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HedgeButton.Action.Filled(
                            text = text,
                            buttonColors = HedgeButton.Action.Color.Filled.Secondary,
                            size = HedgeButton.Action.Size.Medium,
                            modifier = Modifier.weight(1f),
                            onClick = onClickCancel
                        )
                        HedgeButton.Action.Filled(
                            text = submitButton.first,
                            size = HedgeButton.Action.Size.Medium,
                            modifier = Modifier.weight(1f),
                            onClick = submitButton.second
                        )
                    }
                } ?: HedgeButton.Action.Filled(
                    text = submitButton.first,
                    size = HedgeButton.Action.Size.Medium,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = submitButton.second
                )
            }
        }
    }
}

