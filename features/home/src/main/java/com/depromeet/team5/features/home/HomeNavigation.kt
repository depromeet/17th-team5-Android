package com.depromeet.team5.features.home

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.Path
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.home.screen.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
object Home

fun NavGraphBuilder.homeScreen(
    navController: NavController,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    onClickRetrospectionDetail: (Int) -> Unit,
    onClickPrincipleDetail: (Int, Path) -> Unit,
    onClickCreatePrinciple: () -> Unit,
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
            onClickRetrospectionDetail = onClickRetrospectionDetail,
            onClickPrincipleDetail = onClickPrincipleDetail,
            onClickCreatePrinciple = onClickCreatePrinciple,
            onShowErrorToast = onShowErrorToast
        )
    }
}

fun NavController.navigateToHome() {
    navigate(Home)
}