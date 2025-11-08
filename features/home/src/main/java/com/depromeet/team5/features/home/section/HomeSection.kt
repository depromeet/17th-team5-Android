package com.depromeet.team5.features.home.section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.BLUE_500
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.RED_500
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.UserStatsInfo
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.ui.model.HedgeBadge
import com.depromeet.team5.features.home.R
import com.depromeet.team5.features.home.component.DashBoardCountItem
import com.depromeet.team5.features.home.component.RetrospectionMasterDetail
import com.depromeet.team5.features.home.screen.RetrospectionSectionState
import com.depromeet.team5.features.home.screen.RetrospectionState
import com.depromeet.team5.features.home.screen.RetrospectionSymbolState

@Composable
fun HomeSection(
    userStatsUiState: HedgeUiState<UserStatsInfo>,
    retrospectionListUiState: HedgeUiState<List<RetrospectionSymbolState>>,
    onDashBoardClick: (Boolean) -> Unit,
    navigateToReason: (Int) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        UserStatsSection(
            userStatsUiState = userStatsUiState,
            onDashBoardClick = onDashBoardClick,
            onShowErrorToast = onShowErrorToast
        )
        Spacer(modifier = Modifier.height(16.dp))

        RetrospectionHistorySection(
            retrospectionListUiState = retrospectionListUiState,
            navigateToReason = navigateToReason,
            onShowErrorToast = onShowErrorToast
        )
    }
}

@Composable
private fun UserStatsSection(
    userStatsUiState: HedgeUiState<UserStatsInfo>,
    onDashBoardClick: (Boolean) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val userStats = userStatsUiState) {
        is HedgeUiState.Success -> {
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
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
                    .border(
                        width = 1.dp,
                        color = Color(0xFFF1F2F4),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .clip(RoundedCornerShape(22.dp))
                    .clickable {
                        onDashBoardClick(true)
                    }
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
                                percentage = userStats.data.percentage,
                                platinum = userStats.data.hedge,
                                gold = userStats.data.gold,
                                silver = userStats.data.silver,
                                bronze = userStats.data.bronze
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
                            count = userStats.data.hedge,
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
                            count = userStats.data.gold,
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
                            count = userStats.data.silver,
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
                            count = userStats.data.bronze,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        is HedgeUiState.Loading -> {}

        is HedgeUiState.Error -> {
            userStats.throwable?.let {
                onShowErrorToast(it)
            }
        }

    }
}

@Composable
private fun RetrospectionHistorySection(
    retrospectionListUiState: HedgeUiState<List<RetrospectionSymbolState>>,
    navigateToReason: (Int) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(id = R.string.home_tab_retrospection_history),
            style = HedgeTypography.Headline2.SemiBold,
            color = HedgeColor.Text.Title,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
        )

        when (val uiState = retrospectionListUiState) {
            is HedgeUiState.Success -> {
                val retrospectionList = uiState.data

                if (retrospectionList.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.home_tab_retrospection_empty),
                        style = HedgeTypography.Headline2.SemiBold,
                        color = HedgeColor.Text.Assistive,
                        modifier = Modifier
                            .padding(bottom = 100.dp)
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                } else {
                    RetrospectionMasterDetail(
                        companyNames = retrospectionList,
                        navigateToReason = navigateToReason,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            is HedgeUiState.Loading -> {

            }

            is HedgeUiState.Error -> {
                uiState.throwable?.let {
                    onShowErrorToast(it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSectionPreview() {
    val sampleSections = listOf(
        RetrospectionSectionState(
            title = "이번달 회고",
            items = listOf(
                RetrospectionState(
                    id = 1,
                    dayText = "9월 15일",
                    price = 85000,
                    volume = 8,
                    tradeLabelRes = R.string.home_tab_retrospection_trade_sell,
                    tradeColor = BLUE_500,
                    orderDateText = "2025.09.14"
                ),
                RetrospectionState(
                    id = 2,
                    dayText = "9월 15일",
                    price = 85000,
                    volume = 8,
                    tradeLabelRes = R.string.home_tab_retrospection_trade_buy,
                    tradeColor = RED_500,
                    orderDateText = "2025.09.06"
                ),
                RetrospectionState(
                    id = 8,
                    dayText = "9월 8일",
                    price = 85000,
                    volume = 8,
                    tradeLabelRes = R.string.home_tab_retrospection_trade_buy,
                    tradeColor = RED_500,
                    orderDateText = "2025.09.06"
                )
            )
        ),
        RetrospectionSectionState(
            title = "지난달 회고",
            items = listOf(
                RetrospectionState(
                    id = 3,
                    dayText = "8월 14일",
                    price = 85000,
                    volume = 8,
                    tradeLabelRes = R.string.home_tab_retrospection_trade_buy,
                    tradeColor = RED_500,
                    orderDateText = "2025.08.12"
                )
            )
        )
    )

    val successList = HedgeUiState.Success(
        listOf(
            RetrospectionSymbolState(companyName = "삼성전자", sections = sampleSections),
            RetrospectionSymbolState(companyName = "애플", sections = sampleSections)
        )
    )

    val successStats = HedgeUiState.Success(
        UserStatsInfo(percentage = 72, hedge = 1, bronze = 2, silver = 5, gold = 3)
    )

    HomeSection(
        userStatsUiState = successStats,
        retrospectionListUiState = successList,
        onDashBoardClick = {},
        navigateToReason = {},
        onShowErrorToast = {},
    )
}