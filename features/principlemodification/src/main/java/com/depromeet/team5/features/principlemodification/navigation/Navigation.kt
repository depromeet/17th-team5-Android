package com.depromeet.team5.features.principlemodification.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.IS_UPDATED
import com.depromeet.team5.features.principlemodification.screen.PrincipleModificationRoute
import kotlinx.serialization.Serializable


@Serializable
data class PrincipleModification(
    val principleId: Int?,
    val groupName: String?,
    val principle: String?,
    val description: String?
)

fun NavController.navigateToPrincipleModification(
    principleId: Int?,
    groupName: String?,
    principle: String?,
    description: String?,
    navOptions: NavOptions? = null
) {
    navigate(
        PrincipleModification(
            principleId = principleId,
            groupName = groupName,
            principle = principle,
            description = description
        ),
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
        PrincipleModificationRoute(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            onBackClicked = { isUpdated ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(IS_UPDATED, isUpdated)

                navController.popBackStack()
            },
            onShowErrorToast = onShowErrorToast,
            onShowToast = onShowToast,
            onShowNoIconToast = onShowNoIconToast
        )
    }
}