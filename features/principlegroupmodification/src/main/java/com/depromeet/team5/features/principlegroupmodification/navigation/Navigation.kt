package com.depromeet.team5.features.principlegroupmodification.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.IS_UPDATED
import com.depromeet.team5.core.navigation.PrincipleModificationType
import com.depromeet.team5.features.principlegroupmodification.screen.PrincipleGroupModificationRoute
import kotlinx.serialization.Serializable


@Serializable
data class PrincipleGroupModification(
    val groupId: Int,
    val type: PrincipleModificationType
)

fun NavController.navigateToPrincipleGroupModification(
    groupId: Int,
    type: PrincipleModificationType,
    navOptions: NavOptions? = null
) {
    navigate(
        route = PrincipleGroupModification(groupId, type),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.principleGroupModification(
    navController: NavController,
    onShowNoIconToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
) {
    composable<PrincipleGroupModification> {
        PrincipleGroupModificationRoute(
            modifier = Modifier.systemBarsPadding(),
            onShowNoIconToast = onShowNoIconToast,
            onShowErrorToast = onShowErrorToast,
            onBackPressed = { isUpdated ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(IS_UPDATED, isUpdated)

                navController.popBackStack()
            }
        )
    }
}
