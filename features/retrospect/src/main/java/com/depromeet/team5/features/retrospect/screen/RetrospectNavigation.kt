package com.depromeet.team5.features.retrospect.screen

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.model.request.RequestViewModel
import kotlinx.serialization.Serializable

@Serializable
object Retrospect

fun NavController.navigateToRetrospect(){
    navigate(Retrospect)
}

fun NavGraphBuilder.retrospectScreen(
    navController: NavController,
    onBackPressed: () -> Unit
){
    composable<Retrospect>{backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(navController.graph.startDestinationRoute!!)
        }

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        RetrospectRoute(
            onBackPressed = onBackPressed,
            requestViewModel = sharedViewModel
        )
    }
}