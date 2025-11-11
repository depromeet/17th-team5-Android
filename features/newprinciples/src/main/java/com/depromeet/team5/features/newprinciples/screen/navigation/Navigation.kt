package com.depromeet.team5.features.newprinciples.screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.depromeet.team5.features.newprinciples.screen.selectprinciples.SelectPrinciplesRoute
import kotlinx.serialization.Serializable


@Serializable
data class NewPrinciple(
    val groupId: Int,
    val newPrinciples: List<String>
)

@Serializable
data class SelectPrinciple(
    val groupId: Int,
    val newPrinciples: List<String>
)

fun NavController.navigateToNewPrincipleGraph(
    groupId: Int,
    newPrinciples: List<String>,
    navOptions: NavOptions? = null
) {
    navigate(
        route = NewPrinciple(
            groupId = groupId,
            newPrinciples = newPrinciples
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.newPrincipleGraph(
    onBackPressed: () -> Unit
) {
    navigation<NewPrinciple>(
        startDestination = SelectPrinciple::class
    ) {
        composable<SelectPrinciple> { backStackEntry ->
            SelectPrinciplesRoute(
                onBackPressed = onBackPressed
            )
        }
    }
}
