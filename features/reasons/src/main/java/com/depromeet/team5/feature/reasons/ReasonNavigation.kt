package com.depromeet.team5.feature.reasons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.depromeet.team5.core.navigation.request.RequestViewModel
import kotlinx.serialization.Serializable

@Serializable
object ReasonGraph

@Serializable
object Reason

@Serializable
data class ImageDetail(val principleIndex: Int, val imageIndex: Int)

fun NavGraphBuilder.reasonGraph(
    navController: NavController,
    onClickBack: () -> Unit,
    onClickDone: () -> Unit,
) {
    navigation<ReasonGraph>(
        startDestination = Reason,
    ) {
        composable<Reason> { backStackEntry ->

            ReasonRoute(
                onClickBack = onClickBack,
                onClickDone = onClickDone,
                onClickImage = { navController.navigateToImageDetail(it) },
                viewModel = backStackEntry.getReasonViewModel(navController),
                requestViewModel = backStackEntry.getRequestViewModel(navController)
            )
        }

        composable<ImageDetail> { backStackEntry ->
            val imageDetail = backStackEntry.toRoute<ImageDetail>()
            ImageDetailRoute(
                principleIndex = imageDetail.principleIndex,
                imageIndex = imageDetail.imageIndex,
                onClickBack = onClickBack,
                viewModel = backStackEntry.getReasonViewModel(navController)
            )
        }
    }
}

@Composable
private fun NavBackStackEntry.getRequestViewModel(navController: NavController): RequestViewModel {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navController.graph.startDestinationRoute!!)
    }
    return hiltViewModel(parentEntry)
}

@Composable
private fun NavBackStackEntry.getReasonViewModel(navController: NavController): ReasonsViewModel {
    val parentEntry = remember(this) {
        navController.getBackStackEntry<ReasonGraph>()
    }
    return hiltViewModel(parentEntry)
}

fun NavController.navigateToReasonGraph() {
    navigate(ReasonGraph)
}

private fun NavController.navigateToImageDetail(imageDetail: ImageDetail) {
    navigate(imageDetail)
}