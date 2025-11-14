package com.depromeet.team5.features.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

@Composable
fun OrderTypeButton(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = HedgeTypography.Label1.SemiBold,
        color = if (isSelected) HedgeColor.Neutral.BackgroundDefault else HedgeColor.Text.Primary,
        modifier = modifier
            .background(
                color = if (isSelected) HedgeColor.Text.Title else HedgeColor.Neutral.BackgroundSecondary,
                shape = RoundedCornerShape(100.dp)
            )
            .clip(RoundedCornerShape(100.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun OrderTypeButtonPreview() {
    OrderTypeButton(
        title = "매수",
        isSelected = false,
        onClick = {}
    )
}