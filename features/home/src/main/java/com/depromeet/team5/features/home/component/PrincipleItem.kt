package com.depromeet.team5.features.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

@Composable
fun PrincipleItem(
    icon: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(start = 20.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(color = HedgeColor.Neutral.BackgroundSecondary, shape = CircleShape)
                    .size(32.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = icon,
                    style = HedgeTypography.Body3.SemiBold
                )
            }
            Spacer(Modifier.width(12.dp))

            Text(
                text = title,
                style = HedgeTypography.Body3.Medium,
                color = HedgeColor.Text.Title
            )
        }

        Icon(
            imageVector = HedgeIcon.ArrowRightThin,
            contentDescription = null,
            tint = HedgeColor.Text.Disabled,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrincipleItemPreview() {
    PrincipleItem(
        icon = "🔥",
        title = "초보자를 위한 매수 원칙"
    )
}