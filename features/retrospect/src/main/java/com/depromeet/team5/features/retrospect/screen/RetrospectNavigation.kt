package com.depromeet.team5.features.retrospect.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.graphkey.Home
import com.depromeet.team5.core.navigation.request.RequestViewModel
import kotlinx.serialization.Serializable

@Serializable
object Retrospect

fun NavController.navigateToRetrospect() {
    navigate(Retrospect)
}

fun NavGraphBuilder.retrospectScreen(
    navController: NavController,
    onClickedConfirmButton: () -> Unit,
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit
) {
    composable<Retrospect> { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(Home)
        }

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        RetrospectRoute(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            onBackPressed = onBackPressed,
            onClickedConfirmButton = onClickedConfirmButton,
            requestViewModel = sharedViewModel,
            onShowErrorToast = onShowErrorToast
        )
    }
}