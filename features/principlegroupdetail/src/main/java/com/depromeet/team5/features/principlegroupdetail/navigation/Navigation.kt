package com.depromeet.team5.features.principlegroupdetail.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.depromeet.team5.core.navigation.Path
import com.depromeet.team5.core.navigation.graphkey.PrincipleGraph
import com.depromeet.team5.core.navigation.request.PrincipleGraphViewModel

import com.depromeet.team5.features.principlegroupdetail.screen.PrincipleDetailRoute
import kotlinx.serialization.Serializable


@Serializable
data class PrincipleGroupDetail(
    val groupId: Int,
    val path: Path
)

fun NavController.navigateToPrincipleGroupDetail(
    groupId: Int,
    path: Path,
    options: NavOptions? = null
) {
    navigate(
        route = PrincipleGroupDetail(groupId, path),
        navOptions = options
    )
}

fun NavGraphBuilder.principleGroupDetail(
    navController: NavController,
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    onShowToast: (String) -> Unit,
    onShowNoIconToast: (String) -> Unit,
    onNavigatedPrincipleModification: () -> Unit
) {
    composable<PrincipleGroupDetail> { backstackEntry ->
        val args = backstackEntry.toRoute<PrincipleGroupDetail>()

        val subgraphBackstackEntry = remember(backstackEntry) {
            navController.getBackStackEntry(route = PrincipleGraph::class)
        }

        val principleGraphViewModel = hiltViewModel<PrincipleGraphViewModel>(subgraphBackstackEntry)

        PrincipleDetailRoute(
            path = args.path,
            navController = navController,
            onBackPressed = onBackPressed,
            onShowErrorToast = onShowErrorToast,
            onShowToast = onShowToast,
            onShowNoIconToast = onShowNoIconToast,
            graphViewModel = principleGraphViewModel,
            onNavigatedPrincipleModification = {
                onNavigatedPrincipleModification()
            }
        )
    }
}
