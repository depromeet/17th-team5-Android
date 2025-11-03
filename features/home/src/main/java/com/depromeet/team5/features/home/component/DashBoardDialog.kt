package com.depromeet.team5.features.home.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.ui.model.HedgeBadge
import com.depromeet.team5.features.home.R

@Composable
fun DashBoardDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = HedgeColor.WHITE, shape = RoundedCornerShape(24.dp))
            .width(IntrinsicSize.Max)
            .padding(16.dp)
    ) {
        HedgeBadge.entries.asReversed().forEach {
            BadgeDescriptionItem(
                iconResId = it.iconRes,
                title = it.titleRes,
                description = it.descriptionRes
            )
        }

        HedgeButton.Action.Filled(
            text = stringResource(R.string.home_tab_dialog_confirm_button),
            buttonColors = HedgeButton.Action.Color.Filled.Secondary,
            size = HedgeButton.Action.Size.Medium,
            onClick = onDismissRequest,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun BadgeDescriptionItem(
    @DrawableRes iconResId: Int,
    @StringRes title: Int,
    @StringRes description: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 24.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp, 38.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(title),
            style = HedgeTypography.Body1.SemiBold,
            color = HedgeColor.Text.Title,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = stringResource(description),
            style = HedgeTypography.Body3.Medium,
            color = HedgeColor.Text.Primary,
            modifier = Modifier.width(265.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DashBoardDialogPreview() {
    DashBoardDialog(
        onDismissRequest = {}
    )
}