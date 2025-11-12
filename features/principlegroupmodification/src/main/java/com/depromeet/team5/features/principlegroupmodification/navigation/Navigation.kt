package com.depromeet.team5.features.principlegroupmodification.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.depromeet.team5.features.principlegroupmodification.screen.PrincipleGroupModificationRoute
import kotlinx.serialization.Serializable


@Serializable
data class PrincipleGroupModification(
    val groupId: Int
)

fun NavController.navigateToPrincipleGroupModification(
    groupId: Int,
    navOptions: NavOptions? = null
) {
    navigate(
        route = PrincipleGroupModification(groupId),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.principleGroupModification(
    onShowToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    onBackPressed: () -> Unit
) {
    composable<PrincipleGroupModification> {
        PrincipleGroupModificationRoute(
            modifier = Modifier.systemBarsPadding(),
            onShowToast = onShowToast,
            onShowErrorToast = onShowErrorToast,
            onBackPressed = onBackPressed
        )
    }
}
