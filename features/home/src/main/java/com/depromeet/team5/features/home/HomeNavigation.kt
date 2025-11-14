package com.depromeet.team5.features.home

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.navigation.HIGHLIGHT_RETROSPECTION_ID
import com.depromeet.team5.core.navigation.Path
import com.depromeet.team5.core.navigation.graphkey.Home
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.home.screen.HomeRoute

fun NavGraphBuilder.homeScreen(
    navController: NavController,
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    onClickRetrospectionDetail: (Int) -> Unit,
    onClickPrincipleDetail: (Int, Path, OrderType) -> Unit,
    onClickCreatePrinciple: (OrderType) -> Unit,
    onShowErrorToast: (Throwable) -> Unit
) {
    composable<Home> { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(Home)
        }

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        val stateFlow = navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<Int?>(HIGHLIGHT_RETROSPECTION_ID, null)

        val incomingState = stateFlow?.collectAsStateWithLifecycle(null)
        val incomingId = incomingState?.value

        LaunchedEffect(incomingId) {
            if (incomingId != null) {
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.remove<Int>(HIGHLIGHT_RETROSPECTION_ID)
            }
        }

        HomeRoute(
            navController = navController,
            onBuyClick = onBuyClick,
            onSellClick = onSellClick,
            requestViewModel = sharedViewModel,
            onClickRetrospectionDetail = onClickRetrospectionDetail,
            onClickPrincipleDetail = onClickPrincipleDetail,
            onClickCreatePrinciple = onClickCreatePrinciple,
            onShowErrorToast = onShowErrorToast,
            incomingHighlightId = incomingId
        )
    }
}

fun NavController.navigateToHome() {
    navigate(Home)
}