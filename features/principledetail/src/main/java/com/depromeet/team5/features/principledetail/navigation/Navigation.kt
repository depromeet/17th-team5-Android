package com.depromeet.team5.features.principledetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.depromeet.team5.core.navigation.PrincipleType
import com.depromeet.team5.features.principledetail.screen.PrincipleDetailRoute
import kotlinx.serialization.Serializable


@Serializable
data class PrincipleDetail(
    val groupId: Int,
    val principleType: PrincipleType
)

fun NavController.navigateToPrincipleDetail(
    groupId: Int,
    principleType: PrincipleType,
    options: NavOptions? = null
) {
    navigate(
        route = PrincipleDetail(groupId, principleType),
        navOptions = options
    )
}

fun NavGraphBuilder.principleDetail(
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit
) {
    composable<PrincipleDetail> { backstackEntry ->
        val args = backstackEntry.toRoute<PrincipleDetail>()

        PrincipleDetailRoute(
            principleType = args.principleType,
            onBackPressed = onBackPressed,
            onShowErrorToast = onShowErrorToast,
        )
    }
}
