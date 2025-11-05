package com.depromeet.team5.features.home.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.DefaultPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.RecommendedPrinciple
import com.depromeet.team5.core.domain.model.UserStatsInfo
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.home.R
import com.depromeet.team5.features.home.component.DashBoardDialog
import com.depromeet.team5.features.home.component.HomeFloatingActionButton
import com.depromeet.team5.features.home.section.HomeSection
import com.depromeet.team5.features.home.section.PrincipleSection

@Composable
fun HomeRoute(
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    modifier: Modifier = Modifier,
    requestViewModel: RequestViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val userStatsUiState by homeViewModel.userStatsUiState.collectAsStateWithLifecycle()
    val retrospectionListUiState by homeViewModel.retrospectionListUiState.collectAsStateWithLifecycle()

    val selectedOrderType by homeViewModel.principleOrderType.collectAsStateWithLifecycle()
    val principleGroupsUiState by homeViewModel.principleGroupsUiState.collectAsStateWithLifecycle()

    val recommendedUiState by homeViewModel.recommendedPrinciplesUiState.collectAsStateWithLifecycle()
    val defaultsUiState by homeViewModel.defaultPrinciplesUiState.collectAsStateWithLifecycle()

    HomeScreen(
        userStatsUiState = userStatsUiState,
        retrospectionListUiState = retrospectionListUiState,
        recommendedUiState = recommendedUiState,
        defaultsUiState = defaultsUiState,
        selectedOrderType = selectedOrderType,
        principleGroupsUiState = principleGroupsUiState,
        onChangePrincipleOrderType = { homeViewModel.setPrincipleOrderType(it) },
        onBuyClick = {
            requestViewModel.request =
                requestViewModel.request.copy(orderType = OrderType.BUY)
            onBuyClick()
        },
        onSellClick = {
            requestViewModel.request =
                requestViewModel.request.copy(orderType = OrderType.SELL)
            onSellClick()
        },
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
    )
}

@Composable
private fun HomeScreen(
    userStatsUiState: HedgeUiState<UserStatsInfo>,
    retrospectionListUiState: HedgeUiState<List<RetrospectionSymbolState>>,
    recommendedUiState: HedgeUiState<List<RecommendedPrinciple>>,
    defaultsUiState: HedgeUiState<List<DefaultPrinciple>>,
    selectedOrderType: OrderType,
    principleGroupsUiState: HedgeUiState<List<MyPrincipleGroup>>,
    onChangePrincipleOrderType: (OrderType) -> Unit,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var fabChecked by rememberSaveable { mutableStateOf(false) }
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.HOME) }
    var isDashBoardVisible by rememberSaveable { mutableStateOf(false) }

    if (isDashBoardVisible) {
        Dialog(
            onDismissRequest = { isDashBoardVisible = false }
        ) {
            DashBoardDialog(
                onDismissRequest = { isDashBoardVisible = false }
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth()) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_setting),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 11.dp, horizontal = 16.dp)
                    .size(24.dp)
                    .align(Alignment.End),
                tint = HedgeColor.Text.Assistive
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                HomeTab.entries.forEach { tab ->
                    val selected = tab == selectedTab
                    Text(
                        text = stringResource(id = tab.title),
                        style = HedgeTypography.Headline1.SemiBold,
                        color = if (selected) tab.selectedColor else tab.unselectedColor,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { selectedTab = tab }
                    )
                }
            }

            when (selectedTab) {
                HomeTab.HOME -> HomeSection(
                    userStatsUiState = userStatsUiState,
                    retrospectionListUiState = retrospectionListUiState,
                    onDashBoardClick = { isDashBoardVisible = it }
                )

                HomeTab.PRINCIPLE -> PrincipleSection(
                    recommendedUiState = recommendedUiState,
                    defaultsUiState = defaultsUiState,
                    selected = selectedOrderType,
                    onSelect = onChangePrincipleOrderType,
                    principleGroupsUiState = principleGroupsUiState
                )
            }
        }

        if (selectedTab == HomeTab.HOME) {
            HomeFloatingActionButton(
                fabChecked = fabChecked,
                onCheckedChange = { fabChecked = !fabChecked },
                onBuyClick = onBuyClick,
                onSellClick = onSellClick,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    HomeScreen(
        userStatsUiState = HedgeUiState.Loading(UserStatsInfo(0, 0, 0, 0, 0)),
        retrospectionListUiState = HedgeUiState.Loading(emptyList()),
        recommendedUiState = HedgeUiState.Loading(emptyList()),
        defaultsUiState = HedgeUiState.Loading(emptyList()),
        selectedOrderType = OrderType.BUY,
        principleGroupsUiState = HedgeUiState.Loading(emptyList()),
        onChangePrincipleOrderType = {},
        onBuyClick = {},
        onSellClick = {}
    )
}