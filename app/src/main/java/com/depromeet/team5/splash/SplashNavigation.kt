package com.depromeet.team5.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.navigation.Splash


fun NavGraphBuilder.splashScreen(
    navigateToLogin: () -> Unit
){
    composable<Splash>{
        SplashRoute(
            navigateToLogin = navigateToLogin
        )
    }
}