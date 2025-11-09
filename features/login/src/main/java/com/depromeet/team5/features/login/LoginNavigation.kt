package com.depromeet.team5.features.login

import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object LoginGraph

@Serializable
object Login

@Serializable
object Splash

@Serializable
object Agreements

fun NavGraphBuilder.loginGraph(
    navController: NavController,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit
) {
    navigation<LoginGraph>(
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashRoute(
                navigateToLogin = { navController.navigate(Login) }
            )
        }

        composable<Login> {
            LoginRoute(
                navigateToAgreements = { navController.navigate(Agreements) }
            )
        }

        composable<Agreements> {
            AgreementsRoute(
                onClickBack = onClickBack,
                navigateToHome = onClickNext
            )
        }
    }
}