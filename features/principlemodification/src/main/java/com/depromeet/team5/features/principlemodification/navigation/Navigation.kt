package com.depromeet.team5.features.principlemodification.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.IS_PRINCIPLE_UPDATED
import com.depromeet.team5.core.navigation.graphkey.PrincipleGraph
import com.depromeet.team5.core.navigation.request.PrincipleGraphViewModel
import com.depromeet.team5.features.principlemodification.screen.PrincipleModificationRoute
import kotlinx.serialization.Serializable


@Serializable
object PrincipleModification


fun NavController.navigateToPrincipleModification(
    navOptions: NavOptions? = null
) {
    navigate(
        PrincipleModification,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.principleModification(
    navController: NavController,
    onShowErrorToast: (Throwable) -> Unit,
    onShowToast: (String) -> Unit,
    onShowNoIconToast: (String) -> Unit
) {
    composable<PrincipleModification> { backStackEntry ->
        val subgraphBackstackEntry = remember(backStackEntry) {
            navController.getBackStackEntry(route = PrincipleGraph::class)
        }

        val principleGraphViewModel = hiltViewModel<PrincipleGraphViewModel>(subgraphBackstackEntry)

        PrincipleModificationRoute(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            onBackClicked = { isUpdated ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(IS_PRINCIPLE_UPDATED, isUpdated)

                navController.popBackStack()
            },
            onShowErrorToast = onShowErrorToast,
            onShowToast = onShowToast,
            onShowNoIconToast = onShowNoIconToast,
            graphViewModel = principleGraphViewModel
        )
    }
}
