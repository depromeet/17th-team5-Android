package com.depromeet.team5.features.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.ui.util.openWebView

@Composable
fun AgreementsRoute(
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AgreementsScreen(
        state = state,
        onClickBack = onClickBack,
        onCheckAll = viewModel::checkAll,
        onCheckItem = { id, checked -> viewModel.checkItem(id, checked) },
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars)
    )
}

@Composable
private fun AgreementsScreen(
    state: AgreementsUiState,
    onClickBack: () -> Unit,
    onCheckAll: (Boolean) -> Unit,
    onCheckItem: (ConsentId, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HedgeTopBar(onClickBack = onClickBack)
            Spacer(Modifier.height(28.dp))

            Text(
                text = stringResource(R.string.agreements_title),
                style = HedgeTypography.Headline1.SemiBold,
                color = HedgeColor.Text.Title,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(Modifier.height(12.dp))

            AgreementRow(
                title = stringResource(R.string.agreements_agree_all),
                checked = state.allChecked,
                onCheckedChange = { onCheckAll(it) },
                variant = AgreementRowVariant.All
            )
            Divider(
                thickness = 1.dp,
                color = HedgeColor.Neutral.BackgroundSecondary,
            )

            state.items.forEach {
                AgreementRow(
                    title = stringResource(it.id.titleRes),
                    checked = it.checked,
                    onCheckedChange = { checked -> onCheckItem(it.id, checked) },
                    link = it.id.link,
                    onClickDetail = {
                        it.id.link?.let { url ->
                            openWebView(context, url)
                        }
                    }
                )
            }
        }

        HedgeButton.Action.Filled(
            text = stringResource(R.string.agreements_start_button),
            onClick = {},
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            enabled = state.canProceed,
            forceClickable = false
        )
    }
}

@Composable
private fun AgreementRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    variant: AgreementRowVariant = AgreementRowVariant.Item,
    link: String? = null,
    onClickDetail: () -> Unit = {},
) {
    val checkInteraction = remember { MutableInteractionSource() }
    val detailInteraction = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .padding(vertical = if (variant == AgreementRowVariant.All) 22.dp else 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (checked) HedgeIcon.AgreementChecked else HedgeIcon.AgreementUnchecked,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 14.dp)
                .clickable(
                    interactionSource = checkInteraction,
                    indication = null
                ) {
                    onCheckedChange(!checked)
                },
            tint = Color.Unspecified
        )

        Text(
            text = title,
            style = if (variant == AgreementRowVariant.All) HedgeTypography.Body1.SemiBold else HedgeTypography.Body3.Medium,
            color = HedgeColor.Text.Secondary,
        )

        if (link != null) {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = HedgeIcon.ArrowRightThin,
                contentDescription = null,
                modifier = Modifier.clickable(
                    interactionSource = detailInteraction,
                    indication = null
                ) {
                    onClickDetail()
                },
                tint = HedgeColor.Text.Assistive
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AgreementsScreenPreview() {
    AgreementsScreen(
        state = AgreementsUiState(),
        onClickBack = {},
        onCheckAll = {},
        onCheckItem = { _, _ -> }
    )
}