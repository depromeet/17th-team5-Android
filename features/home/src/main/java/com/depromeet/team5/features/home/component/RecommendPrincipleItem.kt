package com.depromeet.team5.features.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.features.home.R
import com.depromeet.team5.features.home.screen.RecommendPrinciple

@Composable
fun RecommendPrincipleItem(
    recommendPrinciple: RecommendPrinciple,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(18.dp),
                spotColor = Color(0x140D0F26),
                clip = false
            )
            .background(
                color = HedgeColor.Neutral.BackgroundDefault,
                shape = RoundedCornerShape(18.dp)
            )
            .border(width = 1.dp, color = Color(0xFFF1F2F4), shape = RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .clickable {
            }
            .size(150.dp, 165.dp)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.TopEnd)
                .drawWithCache {
                    val centerColor = Color(0xFF1CCAFF).copy(alpha = 0.24f)
                    val edgeColor = Color(0xFF1CCAFF).copy(alpha = 0f)
                    val radius = size.width * 0.55f
                    val center = Offset(
                        x = size.width * 0.85f,
                        y = -size.height * 0.15f
                    )

                    val brush = Brush.radialGradient(
                        colors = listOf(centerColor, edgeColor),
                        center = center,
                        radius = radius
                    )
                    onDrawBehind { drawRect(brush) }
                }
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.TopStart)
                .drawWithCache {
                    val centerColor = Color(0xFF29F980).copy(alpha = 0.16f)
                    val edgeColor = Color(0xFF29F980).copy(alpha = 0f)
                    val radius = size.width * 0.55f
                    val center = Offset(
                        x = size.width * 0.15f,
                        y = -size.height * 0.15f
                    )

                    val brush = Brush.radialGradient(
                        colors = listOf(centerColor, edgeColor),
                        center = center,
                        radius = radius
                    )
                    onDrawBehind { drawRect(brush) }
                }
        )

        Column(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(recommendPrinciple.imgResId),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clip(CircleShape)
                        .size(24.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = stringResource(recommendPrinciple.celebResId),
                    style = HedgeTypography.Label2.Medium,
                    color = Color(0xFF000000).copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.padding(bottom = 36.dp))

            Text(
                text = stringResource(recommendPrinciple.principleGroupTitleResId),
                style = HedgeTypography.Body3.SemiBold,
                color = HedgeColor.Text.Title,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(
                    id = R.string.principle_tab_recommend_principle_count,
                    recommendPrinciple.count
                ),
                style = HedgeTypography.Label2.Medium,
                color = HedgeColor.Brand.Darken,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecommendPrincipleItemPreview() {
    RecommendPrincipleItem(
        recommendPrinciple = RecommendPrinciple.WARREN_BUFFETT
    )
}