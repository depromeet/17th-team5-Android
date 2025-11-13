package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeShadow
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

@Composable
fun RestrictionIndicatorContainer(
    checkedAdherenceCount: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    0f to Color.Transparent,
                    0.2356f to HedgeColor.Neutral.BackgroundDefault.copy(0.2f),
                    1f to HedgeColor.Neutral.BackgroundDefault.copy(0.98f)
                ),
            )
            .padding(bottom = 32.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        RestrictionIndicator(
            checkedAdherenceCount = checkedAdherenceCount,
            pageSize = pagerState.pageCount,
            currentPage = pagerState.currentPage,
        )
    }
}

@Composable
private fun RestrictionIndicator(
    checkedAdherenceCount: Int,
    pageSize: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(29.dp)
    Row(
        modifier = modifier
            .dropShadow(
                shape = shape,
                shadow = HedgeShadow.Medium,
            )
            .background(
                color = HedgeColor.Neutral.BackgroundDefault,
                shape = shape,
            )
            .padding(start = 14.dp, top = 12.dp, bottom = 12.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RestrictionProgress(
            checkedAdherenceCount = checkedAdherenceCount,
            pageSize = pageSize,
        )
        repeat(pageSize) { idx ->
            val color = if (currentPage == idx) HedgeColor.Brand.Primary else HedgeColor.Brand.Disabled
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp)
            )
        }
    }
}

@Composable
private fun RestrictionProgress(
    checkedAdherenceCount: Int,
    pageSize: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(color = HedgeColor.Neutral.BackgroundSecondary, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            strokeCap = StrokeCap.Round,
            strokeWidth = 3.dp,
            modifier = Modifier.size(26.dp),
            color = HedgeColor.Brand.Primary,
            progress = checkedAdherenceCount / pageSize.toFloat(),
        )
        Text(
            text = "\uD83D\uDD25",
            style = HedgeTypography.Caption2.Regular,
        )
    }
}