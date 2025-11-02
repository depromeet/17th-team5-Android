package com.depromeet.team5.features.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.ui.model.HedgeBadge
import com.depromeet.team5.features.home.component.DashBoardCountItem

@Composable
fun HomeSection(
    percentage: Int,
    bronze: Int,
    silver: Int,
    gold: Int,
    platinum: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(22.dp),
                    spotColor = Color(0x140D0F26),
                    clip = false
                )
                .background(
                    color = HedgeColor.Neutral.BackgroundDefault,
                    shape = RoundedCornerShape(22.dp)
                )
                .border(width = 1.dp, color = Color(0xFFF1F2F4), shape = RoundedCornerShape(22.dp))
                .clip(RoundedCornerShape(22.dp))
                .fillMaxWidth()
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
                            y = -size.height * 0.55f
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
                            y = -size.height * 0.55f
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
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(
                        dashboardTitleRes(
                            percentage = percentage,
                            platinum = platinum,
                            gold = gold,
                            silver = silver,
                            bronze = bronze
                        )
                    ),
                    style = HedgeTypography.Body2.SemiBold,
                    color = HedgeColor.Text.Primary
                )

                Row(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DashBoardCountItem(
                        imgResId = HedgeBadge.PLATINUM.iconRes,
                        count = platinum,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                            .width(1.dp)
                            .fillMaxHeight()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(HedgeColor.Neutral.BackgroundSecondary)
                    )

                    DashBoardCountItem(
                        imgResId = HedgeBadge.GOLD.iconRes,
                        count = gold,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                            .width(1.dp)
                            .fillMaxHeight()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(HedgeColor.Neutral.BackgroundSecondary)
                    )

                    DashBoardCountItem(
                        imgResId = HedgeBadge.SILVER.iconRes,
                        count = silver,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = modifier
                            .padding(horizontal = 20.dp)
                            .width(1.dp)
                            .fillMaxHeight()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(HedgeColor.Neutral.BackgroundSecondary)
                    )

                    DashBoardCountItem(
                        imgResId = HedgeBadge.BRONZE.iconRes,
                        count = bronze,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSectionPreview() {
    HomeSection(
        percentage = 10,
        bronze = 2,
        silver = 3,
        gold = 4,
        platinum = 5
    )
}