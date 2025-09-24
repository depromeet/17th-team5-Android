package com.depromeet.team5.features.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
enum class TradeType { BUY, SELL }

@Serializable
data class Search(val trade: TradeType)

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit
) {
    composable<Search> {
        SearchRoute(
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToSearch(trade: TradeType) {
    navigate(Search(trade))
}