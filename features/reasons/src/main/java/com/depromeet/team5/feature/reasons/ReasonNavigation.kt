package com.depromeet.team5.feature.reasons

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Reason

fun NavGraphBuilder.reasonScreen(
    onClickBack: () -> Unit
) {
    composable<Reason> {
        ReasonRoute(
            onClickBack = onClickBack
        )
    }
}

fun NavController.navigateToReason() {
    navigate(Reason)
}