package com.depromeet.team5.splash

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.Splash


fun NavGraphBuilder.splashScreen(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit
) {
    composable<Splash>(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        SplashRoute(
            navigateToHome = navigateToHome,
            navigateToLogin = navigateToLogin
        )
    }
}