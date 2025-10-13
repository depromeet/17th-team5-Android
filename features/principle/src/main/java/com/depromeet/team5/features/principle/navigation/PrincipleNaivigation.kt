package com.depromeet.team5.features.principle.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.principle.PrincipleRoute
import kotlinx.serialization.Serializable


@Serializable
object Principle

fun NavController.navigateToPrinciple() {
    navigate(route = Principle)
}

fun NavGraphBuilder.principleScreen(
    navController: NavController,
    onClickNext: () -> Unit,
    onBackPressed: () -> Unit
) {
    composable<Principle> { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(navController.graph.startDestinationRoute!!)
        }

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        PrincipleRoute(
            onBackPressed = onBackPressed,
            requestViewModel = sharedViewModel,
            onClickNext = onClickNext
        )
    }
}
