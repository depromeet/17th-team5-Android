package com.depromeet.team5

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.depromeet.team5.features.home.Home
import com.depromeet.team5.features.home.homeScreen
import com.depromeet.team5.features.search.TradeType
import com.depromeet.team5.features.search.navigateToSearch
import com.depromeet.team5.features.search.searchScreen

@Composable
fun HedgeNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ) {
        homeScreen(
            onBuyClick = { navController.navigateToSearch(TradeType.BUY) },
            onSellClick = { navController.navigateToSearch(TradeType.SELL) }
        )

        searchScreen(
            onBackClick = { navController.popBackStack() }
        )
    }
}