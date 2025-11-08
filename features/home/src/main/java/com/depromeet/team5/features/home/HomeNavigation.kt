package com.depromeet.team5.features.home

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.home.screen.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
object Home

fun NavGraphBuilder.homeScreen(
    navController: NavController,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    onRetrospectClick: (Int) -> Unit,
    onPrincipleClick: (Int) -> Unit,
    onCreatePrincipleClick: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit
) {
    composable<Home> { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(navController.graph.startDestinationRoute!!)
        }

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        HomeRoute(
            onBuyClick = onBuyClick,
            onSellClick = onSellClick,
            requestViewModel = sharedViewModel,
            onRetrospectClick = onRetrospectClick,
            onPrincipleClick = onPrincipleClick,
            onCreatePrincipleClick = onCreatePrincipleClick,
            onShowErrorToast = onShowErrorToast
        )
    }
}

fun NavController.navigateToHome() {
    navigate(Home)
}