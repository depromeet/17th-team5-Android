package com.depromeet.team5.feature.reasons

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class RetrospectionDetail(val retrospectionId: Int)

fun NavGraphBuilder.retrospectionDetailNavigation(
    onClickBack: () -> Unit,
    onClickFeedback: (Int) -> Unit,
    onClickImage: (ImageDetail) -> Unit,
    onShowToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
) {
    composable<RetrospectionDetail> { backStackEntry ->
        RetrospectionDetailRoute(
            onClickBack = onClickBack,
            onClickFeedback = onClickFeedback,
            onClickImage = onClickImage,
            onShowToast = onShowToast,
            onShowErrorToast = onShowErrorToast,
        )
    }
}

fun NavController.navigateToRetrospectionDetail(retrospectionId: Int) {
    navigate(RetrospectionDetail(retrospectionId))
}