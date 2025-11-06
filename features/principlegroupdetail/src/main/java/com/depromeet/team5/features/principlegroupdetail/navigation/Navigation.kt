package com.depromeet.team5.features.principlegroupdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.depromeet.team5.core.navigation.PrincipleType
import com.depromeet.team5.features.principlegroupdetail.screen.PrincipleDetailRoute
import kotlinx.serialization.Serializable


@Serializable
data class PrincipleGroupDetail(
    val groupId: Int,
    val principleType: PrincipleType
)

fun NavController.navigateToPrincipleGroupDetail(
    groupId: Int,
    principleType: PrincipleType,
    options: NavOptions? = null
) {
    navigate(
        route = PrincipleGroupDetail(groupId, principleType),
        navOptions = options
    )
}

fun NavGraphBuilder.principleGroupDetail(
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    onShowToast: (String) -> Unit,
    onNavigatedPrincipleModification: (Int, String, String, String) -> Unit
) {
    composable<PrincipleGroupDetail> { backstackEntry ->
        val args = backstackEntry.toRoute<PrincipleGroupDetail>()

        PrincipleDetailRoute(
            principleType = args.principleType,
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
