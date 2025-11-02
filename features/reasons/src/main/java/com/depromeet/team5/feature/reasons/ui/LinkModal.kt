package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.feature.reasons.R

@Composable
fun LinkModal(
    showDialog: Boolean,
    onClickSubmit: (String) -> Unit,
    onClickCancel: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (showDialog) {
        var text by remember { mutableStateOf("") }
        Dialog(
            onDismissRequest = onDismissRequest,
        ) {
            Column(
                modifier = modifier
                    .heightIn(184.dp)
                    .background(
                        HedgeColor.Neutral.BackgroundDefault,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    stringResource(R.string.link_modal_title),
                    style = HedgeTypography.Body1.SemiBold,
                    color = HedgeColor.Text.Title,
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                )
                Spacer(Modifier.size(12.dp))
                BasicTextField(
                    modifier = Modifier
                        .border(
                            width = (1.2).dp,
                            color = HedgeColor.Neutral.BackgroundSecondary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                        .fillMaxWidth(),
                    value = text,
                    onValueChange = { text = it },
                    textStyle = HedgeTypography.Body3.Medium.copy(
                        color = HedgeColor.Text.Primary,
                    )
                ) { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = stringResource(R.string.link_input),
                            style = HedgeTypography.Body3.Medium,
                            color = HedgeColor.Text.Assistive
                        )
                    }
                    innerTextField()
                }
                Spacer(Modifier.size(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HedgeButton.Action.Filled(
                        text = stringResource(R.string.cancel),
                        buttonColors = HedgeButton.Action.Color.Filled.Secondary,
                        size = HedgeButton.Action.Size.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = onClickCancel
                    )
                    HedgeButton.Action.Filled(
                        text = stringResource(R.string.add),
                        size = HedgeButton.Action.Size.Medium,
                        modifier = Modifier.weight(1f),
                        onClick = { onClickSubmit(text) }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun LinkModalPreview() {
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
            .background(HedgeColor.Neutral.BackgroundDefault)
    ) {
        var showDialog by remember { mutableStateOf(true) }
        LinkModal(
            showDialog = showDialog,
            onClickSubmit = {},
            onClickCancel = {},
            onDismissRequest = { showDialog = false },
        )
    }
}