package com.depromeet.team5.features.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.DefaultPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.RecommendedPrinciple
import com.depromeet.team5.core.domain.model.UserStatsInfo
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.navigation.IS_UPDATED
import com.depromeet.team5.core.navigation.Path
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.features.home.component.DashBoardDialog
import com.depromeet.team5.features.home.component.HomeFloatingActionButton
import com.depromeet.team5.features.home.section.HomeSection
import com.depromeet.team5.features.home.section.PrincipleSection

@Composable
fun HomeRoute(
    navController: NavController,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    onClickRetrospectionDetail: (Int) -> Unit,
    onClickPrincipleDetail: (Int, Path, OrderType) -> Unit,
    onClickCreatePrinciple: (OrderType) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    incomingHighlightId: Int?,
    modifier: Modifier = Modifier,
    requestViewModel: RequestViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val userStatsUiState by homeViewModel.userStatsUiState.collectAsStateWithLifecycle()
    val retrospectionListUiState by homeViewModel.retrospectionListUiState.collectAsStateWithLifecycle()

    val selectedOrderType by homeViewModel.principleOrderType.stateFlow.collectAsStateWithLifecycle()
    val principleGroupsUiState by homeViewModel.principleGroupsUiState.collectAsStateWithLifecycle()

    val recommendedUiState by homeViewModel.recommendedPrinciplesUiState.collectAsStateWithLifecycle()
    val defaultsUiState by homeViewModel.defaultPrinciplesUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<Boolean?>(IS_UPDATED, null)
            ?.baseCollect(
                onSuccess = { isUpdated ->
                    if (isUpdated != null && isUpdated) {
                        homeViewModel.restartPrincipleGroups()
                    }

                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<Boolean>(IS_UPDATED)
                },
                onError = {}
            )
    }

    LaunchedEffect(incomingHighlightId) {
        if (incomingHighlightId != null) {
            homeViewModel.prepareHighlight(incomingHighlightId)
        }
    }
    val highlightIdOnce by homeViewModel.highlightIdOnce.stateFlow.collectAsStateWithLifecycle()

    HomeScreen(
        userStatsUiState = userStatsUiState,
        retrospectionListUiState = retrospectionListUiState,
        recommendedUiState = recommendedUiState,
        defaultsUiState = defaultsUiState,
        selectedOrderType = selectedOrderType,
        principleGroupsUiState = principleGroupsUiState,
        onChangePrincipleOrderType = { homeViewModel.setPrincipleOrderType(it) },
        onClickRetrospectionDetail = onClickRetrospectionDetail,
        onClickPrincipleDetail = { groupId, path ->
            onClickPrincipleDetail(groupId, path, selectedOrderType)
        },
        onClickCreatePrinciple = {
            onClickCreatePrinciple(selectedOrderType)
        },
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
        onShowErrorToast = { onShowErrorToast(it) },
        highlightRetrospectionId = highlightIdOnce,
        modifier = modifier
            .background(HedgeColor.Neutral.BackgroundDefault)
            .windowInsetsPadding(WindowInsets.systemBars),
        onClearHighlight = { homeViewModel.clearHighlight() }
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
    onClickRetrospectionDetail: (Int) -> Unit,
    onClickPrincipleDetail: (Int, Path) -> Unit,
    onClickCreatePrinciple: () -> Unit,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    highlightRetrospectionId: Int?,
    onClearHighlight: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interaction = remember { MutableInteractionSource() }
    var fabChecked by rememberSaveable { mutableStateOf(false) }
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.HOME) }
    var isDashBoardVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(highlightRetrospectionId) {
        if (highlightRetrospectionId != null) {
            fabChecked = false
        }
    }

    LaunchedEffect(selectedTab) {
        if (selectedTab != HomeTab.HOME) {
            onClearHighlight()
        }
    }

    if (isDashBoardVisible) {
        DashBoardDialog(
            onDismissRequest = { isDashBoardVisible = false }
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth()) {
            Icon(
                imageVector = HedgeIcon.Setting,
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
                            .clickable(
                                interactionSource = interaction,
                                indication = null
                            ) { selectedTab = tab }
                    )
                }
            }

            when (selectedTab) {
                HomeTab.HOME -> HomeSection(
                    userStatsUiState = userStatsUiState,
                    retrospectionListUiState = retrospectionListUiState,
                    onDashBoardClick = { isDashBoardVisible = it },
                    onClickRetrospectionDetail = onClickRetrospectionDetail,
                    onShowErrorToast = { onShowErrorToast(it) },
                    highlightRetrospectionId = highlightRetrospectionId,
                    onClearHighlight = onClearHighlight
                )

                HomeTab.PRINCIPLE -> PrincipleSection(
                    recommendedUiState = recommendedUiState,
                    defaultsUiState = defaultsUiState,
                    selected = selectedOrderType,
                    onSelect = onChangePrincipleOrderType,
                    principleGroupsUiState = principleGroupsUiState,
                    onClickPrincipleDetail = onClickPrincipleDetail,
                    onClickCreatePrinciple = onClickCreatePrinciple,
                    onShowErrorToast = { onShowErrorToast(it) }
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
        onClickRetrospectionDetail = {},
        onClickPrincipleDetail = { _, _ -> },
        onClickCreatePrinciple = {},
        onBuyClick = {},
        onSellClick = {},
        onShowErrorToast = {},
        highlightRetrospectionId = null,
        onClearHighlight = {}
    )
}