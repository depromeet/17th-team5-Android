package com.depromeet.team5.feature.reasons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.graphkey.Home
import com.depromeet.team5.core.navigation.request.RequestViewModel
import kotlinx.serialization.Serializable

@Serializable
object Reason

fun NavGraphBuilder.reasonNavigation(
    navController: NavController,
    onClickBack: () -> Unit,
    onClickDone: () -> Unit,
    onClickImage: (ImageDetail) -> Unit,
) {
    composable<Reason> { backStackEntry ->
        ReasonRoute(
            onClickBack = onClickBack,
            onClickDone = onClickDone,
            onClickImage = onClickImage,
            requestViewModel = backStackEntry.getRequestViewModel(navController)
        )
    }
}

@Composable
private fun NavBackStackEntry.getRequestViewModel(navController: NavController): RequestViewModel {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(Home)
    }
    return hiltViewModel(parentEntry)
}

fun NavController.navigateToReason() {
    navigate(Reason)
}