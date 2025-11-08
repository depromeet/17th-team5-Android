package com.depromeet.team5.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

@Composable
fun PrincipleThumbnail(
    thumbnail: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ){
        if (thumbnail.startsWith("http")) {
            AsyncImage(
                model = thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(32.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .background(
                        color = HedgeColor.Neutral.BackgroundSecondary,
                        shape = CircleShape
                    )
                    .size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = thumbnail,
                    style = HedgeTypography.Body3.SemiBold
                )
            }
        }
    }
}