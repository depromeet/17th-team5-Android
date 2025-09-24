package com.depromeet.team5.features.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Home

fun NavGraphBuilder.homeScreen() {
    composable<Home> {
        HomeRoute()
    }
}

fun NavController.navigateToHome() {
    navigate(Home)
}