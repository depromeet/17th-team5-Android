package com.depromeet.team5.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.depromeet.team5.core.domain.model.OrderType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.home.component.HomeFloatingActionButton
import com.depromeet.team5.features.home.section.HomeSection
import com.depromeet.team5.features.home.section.PrincipleSection

@Composable
fun HomeRoute(
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    modifier: Modifier = Modifier,
    requestViewModel: RequestViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by homeViewModel.homeUiStateFlow.collectAsStateWithLifecycle()

    when(val uiState = homeUiState){
        is HomeUiState.Success -> {
            HomeScreen(
                percentage = uiState.percentage,
                hedge = uiState.hedge,
                bronze = uiState.bronze,
                silver = uiState.silver,
                gold = uiState.gold,
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
                modifier = modifier
            )
        }

        is HomeUiState.Loading -> {

        }

        is HomeUiState.Error -> {

        }

        is HomeUiState.Failure -> {

        }

    }
}

@Composable
private fun HomeScreen(
    percentage: Int,
    hedge: Int,
    bronze: Int,
    silver: Int,
    gold: Int,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fabChecked by rememberSaveable { mutableStateOf(false) }
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.HOME) }

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
                    percentage = percentage,
                    bronze = bronze,
                    silver = silver,
                    gold = gold,
                    platinum = hedge
                )
                HomeTab.PRINCIPLE -> PrincipleSection()
            }
        }

        HomeFloatingActionButton(
            fabChecked = fabChecked,
            onCheckedChange = { fabChecked = !fabChecked },
            onBuyClick = onBuyClick,
            onSellClick = onSellClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    HomeScreen(
        percentage = 10,
        hedge = 1,
        bronze = 2,
        silver = 3,
        gold = 4,
        onBuyClick = {},
        onSellClick = {}
    )
}