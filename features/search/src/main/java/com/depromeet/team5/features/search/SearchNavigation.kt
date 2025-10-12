package com.depromeet.team5.features.search

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.request.RequestViewModel
import kotlinx.serialization.Serializable

@Serializable
object Search

fun NavGraphBuilder.searchScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    onItemClick: () -> Unit
) {
    composable<Search> { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(navController.graph.startDestinationRoute!!)
        }

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        SearchRoute(
            onBackClick = onBackClick,
            requestViewModel = sharedViewModel,
            onItemClick = onItemClick
        )
    }
}

fun NavController.navigateToSearch() {
    navigate(route = Search)
}
