package com.depromeet.team5.features.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.ui.model.AgreementsType
import com.depromeet.team5.core.ui.util.openWebView
import com.depromeet.team5.core.ui.R as UiR

@Composable
fun SettingRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingScreen(
        onBackClick = onBackClick,
        modifier = modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(color = HedgeColor.Neutral.BackgroundSecondary)
    )
}

@Composable
private fun SettingScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HedgeTopBar(onClickBack = onBackClick)

        Spacer(modifier = Modifier.padding(top = 20.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .background(color = HedgeColor.WHITE, shape = RoundedCornerShape(22.dp))
                .padding(vertical = 20.dp, horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(UiR.string.setting_agreements_title),
                style = HedgeTypography.Body2.SemiBold,
                color = HedgeColor.Text.Primary
            )

            SettingRow(
                title = stringResource(AgreementsType.TERMS.titleRes),
                onDetailClick = {
                    openWebView(context, AgreementsType.TERMS.link.toString())
                },
                modifier = Modifier.padding(top = 20.dp)
            )

            SettingRow(
                title = stringResource(AgreementsType.PRIVACY.titleRes),
                onDetailClick = {
                    openWebView(context, AgreementsType.PRIVACY.link.toString())
                },
                modifier = Modifier.padding(top = 16.dp)
            )

            SettingRow(
                title = stringResource(AgreementsType.MARKETING.titleRes),
                onDetailClick = {
                    openWebView(context, AgreementsType.MARKETING.link.toString())
                },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .background(color = HedgeColor.WHITE, shape = RoundedCornerShape(22.dp))
                .padding(vertical = 20.dp, horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(UiR.string.setting_account_title),
                style = HedgeTypography.Body2.SemiBold,
                color = HedgeColor.Text.Primary
            )

            SettingRow(
                title = stringResource(UiR.string.setting_account_logout),
                onDetailClick = {},
                modifier = Modifier.padding(top = 20.dp)
            )

            SettingRow(
                title = stringResource(UiR.string.setting_account_withdraw),
                onDetailClick = {},
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun SettingRow(
    title: String,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interaction = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .clickable(
                interactionSource = interaction,
                indication = null
            ) { onDetailClick() }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = HedgeTypography.Body3.Medium,
            color = HedgeColor.Text.Secondary
        )
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = HedgeIcon.ArrowRightThin,
            contentDescription = null,
            tint = HedgeColor.Text.Assistive
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingPreview() {
    SettingScreen(
        onBackClick = {}
    )
}