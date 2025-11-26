package com.depromeet.team5.features.newprinciples.screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.depromeet.team5.features.newprinciples.screen.addprinciples.AddPrinciplesRoute
import com.depromeet.team5.features.newprinciples.screen.selectprinciples.SelectPrinciplesRoute
import kotlinx.serialization.Serializable


@Serializable
internal data class NewPrinciplesRoute(
    val groupId: Int,
    val principles: List<String>
)

@Serializable
internal data class SelectPrinciplesRoute(
    val groupId: Int,
    val principles: List<String>
)

@Serializable
internal data class AddPrinciplesRoute(
    val groupId: Int,
    val principles: List<String>
)


fun NavController.navigateToNewPrincipleGraph(
    groupId: Int,
    newPrinciples: List<String>,
    navOptions: NavOptions? = null
) {
    navigate(
        route = NewPrinciplesRoute(
            groupId = groupId,
            principles = newPrinciples
        ),
        navOptions = navOptions
    )
}

fun NavController.navigateToAddPrincipleGraph(
    groupId: Int,
    newPrinciples: List<String>,
    navOptions: NavOptions? = null
) {
    navigate(
        route = AddPrinciplesRoute(
            groupId = groupId,
            principles = newPrinciples
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.newPrincipleGraph(
    navController: NavController,
    onShowToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    onShowNoIconToast: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    navigation<NewPrinciplesRoute>(
        startDestination = SelectPrinciplesRoute::class
    ) {
        composable<SelectPrinciplesRoute> { backStackEntry ->

            val route = backStackEntry.toRoute<SelectPrinciplesRoute>()

            SelectPrinciplesRoute(
                onClickedConfirmButton = { selectedPrinciples ->
                    navController.navigateToAddPrincipleGraph(
                        groupId = route.groupId,
                        newPrinciples = selectedPrinciples
                    )
                },
                onBackPressed = onBackPressed,
                onShowErrorToast = onShowErrorToast
            )
        }

        composable<AddPrinciplesRoute> { backStackEntry ->
            AddPrinciplesRoute(
                onShowToast = onShowToast,
                onShowErrorToast = onShowErrorToast,
                onShowNoIconToast = onShowNoIconToast,
                onBackPressed = onBackPressed,
                onFinished = {
                    navController.popBackStack<SelectPrinciplesRoute>(inclusive = true)
                }
            )
        }
    }
}
