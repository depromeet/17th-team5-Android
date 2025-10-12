package com.depromeet.team5.feature.reasons

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.request.RequestViewModel
import kotlinx.serialization.Serializable

@Serializable
object Reason

fun NavGraphBuilder.reasonScreen(
    navController: NavController,
    onClickBack: () -> Unit,
    onClickDone: () -> Unit,
    onClickEditTradeInfo: () -> Unit
) {
    composable<Reason> { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(navController.graph.startDestinationRoute!!)
        }

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        ReasonRoute(
            onClickBack = onClickBack,
            onClickDone = onClickDone,
            onClickEditTradeInfo = onClickEditTradeInfo,
            requestViewModel = sharedViewModel
        )
    }
}

fun NavController.navigateToReason() {
    navigate(Reason)
}