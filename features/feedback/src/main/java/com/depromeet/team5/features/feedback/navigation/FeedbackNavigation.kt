package com.depromeet.team5.features.feedback.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.model.request.RequestViewModel
import com.depromeet.team5.features.feedback.screen.FeedbackRoute
import kotlinx.serialization.Serializable

@Serializable
object Feedback

fun NavGraphBuilder.feedbackScreen(
    navController: NavController,
    onRemoveClick: () -> Unit
) {
    composable<Feedback> { entry ->

        val parentEntry = remember(entry) {
            navController.getBackStackEntry(navController.graph.startDestinationRoute!!)
        }

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        FeedbackRoute(
            requestViewModel = sharedViewModel,
            onRemoveClick = onRemoveClick
        )
    }
}

fun NavController.navigateToFeedback() {
    navigate(Feedback)
}