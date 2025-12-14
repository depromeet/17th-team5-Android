package com.depromeet.team5.features.login

import androidx.compose.animation.EnterTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object LoginGraph

@Serializable
object Login

@Serializable
object Agreements


fun NavHostController.navigateToLogin() {
    navigate(LoginGraph)
}


fun NavGraphBuilder.loginGraph(
    navController: NavController,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    onLoginKakao: suspend () -> Result<Pair<String, String>>,
) {
    navigation<LoginGraph>(
        startDestination = Login
    ) {
        composable<Login>(
            enterTransition = { EnterTransition.None },
        ) {
            LoginRoute(
                navigateToAgreements = { navController.navigate(Agreements) },
                onLoginKakao = onLoginKakao
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