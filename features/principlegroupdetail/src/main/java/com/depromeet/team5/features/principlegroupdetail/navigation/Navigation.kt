package com.depromeet.team5.features.principlegroupdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.depromeet.team5.core.navigation.Path

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
    onNavigatedPrincipleModification: (Int?, String?, String?, String?) -> Unit
) {
    composable<PrincipleGroupDetail> { backstackEntry ->
        val args = backstackEntry.toRoute<PrincipleGroupDetail>()

        PrincipleDetailRoute(
            path = args.path,
            navController = navController,
            onBackPressed = onBackPressed,
            onShowErrorToast = onShowErrorToast,
            onShowToast = onShowToast,
            onNavigatedPrincipleModification = { principleId, groupName, principle, description ->
                onNavigatedPrincipleModification(
                    principleId,
                    groupName,
                    principle,
                    description
                )
            }
        )
    }
}
