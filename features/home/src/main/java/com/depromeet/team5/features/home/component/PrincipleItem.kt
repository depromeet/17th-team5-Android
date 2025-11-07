package com.depromeet.team5.features.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.ui.component.PrincipleThumbnail

@Composable
fun PrincipleItem(
    id: Int,
    icon: String,
    title: String,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick(id) }
            .padding(start = 20.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            PrincipleThumbnail(icon)
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
        id = 0,
        icon = "🔥",
        title = "초보자를 위한 매수 원칙",
        onClick = {}
    )
}