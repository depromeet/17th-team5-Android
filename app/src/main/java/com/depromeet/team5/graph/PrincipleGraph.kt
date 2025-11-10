package com.depromeet.team5.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import com.depromeet.team5.core.navigation.Path
import com.depromeet.team5.core.navigation.graphkey.PrincipleGraph
import com.depromeet.team5.features.principlegroupdetail.navigation.PrincipleGroupDetail
import com.depromeet.team5.features.principlegroupdetail.navigation.principleGroupDetail
import com.depromeet.team5.features.principlemodification.navigation.navigateToPrincipleModification
import com.depromeet.team5.features.principlemodification.navigation.principleModification



fun NavController.navigatePrincipleGraph(
    groupId: Int,
    path: Path,
    navOptions: NavOptions? = null
) {
    navigate(
        route = PrincipleGraph(groupId, path),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.principleGraph(
    navController: NavController,
    onShowErrorToast: (Throwable) -> Unit,
    onShowToast: (String) -> Unit,
    onShowNoIconToast: (String) -> Unit
) {
    navigation<PrincipleGraph>(
        startDestination = PrincipleGroupDetail::class
    ) {

        principleGroupDetail(
            navController = navController,
            onBackPressed = navController::popBackStack,
            onShowErrorToast = onShowErrorToast,
            onShowToast = onShowToast,
            onNavigatedPrincipleModification = {
                navController.navigateToPrincipleModification()
            }
        )

        principleModification(
            navController = navController,
            onShowErrorToast = onShowErrorToast,
            onShowToast = onShowToast,
            onShowNoIconToast = onShowNoIconToast
        )
    }
}
