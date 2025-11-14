package com.depromeet.team5.feature.reasons

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class ImageDetail(val images: List<String>, val imageIndex: Int)

fun NavGraphBuilder.imageDetailNavigation(
    navController: NavController,
    onClickBack: () -> Unit,
) {
    composable<ImageDetail> { backStackEntry ->
        val imageDetail = backStackEntry.toRoute<ImageDetail>()
        ImageDetailRoute(
            images = imageDetail.images,
            initialIndex = imageDetail.imageIndex,
            onClickBack = onClickBack,
        )
    }
}

fun NavController.navigateToImageDetail(imageDetail: ImageDetail) {
    navigate(imageDetail)
}
