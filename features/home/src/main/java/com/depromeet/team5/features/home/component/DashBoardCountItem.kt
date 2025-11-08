package com.depromeet.team5.features.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.features.home.R

@Composable
fun DashBoardCountItem(
    @DrawableRes imgResId: Int,
    count: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imgResId),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 6.dp)
                .size(
                    width = 32.dp,
                    height = 38.dp
                )
        )

        Text(
            text = stringResource(R.string.home_tab_dashboard_count, count),
            style = HedgeTypography.Label2.Medium,
            color = HedgeColor.Text.Alternative
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DashBoardCountItemPreview() {
    DashBoardCountItem(
        imgResId = com.depromeet.team5.core.ui.R.drawable.img_badge_platinum,
        count = 10
    )
}