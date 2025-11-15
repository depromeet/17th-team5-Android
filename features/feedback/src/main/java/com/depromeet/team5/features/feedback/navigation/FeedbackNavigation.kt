package com.depromeet.team5.features.feedback.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.depromeet.team5.core.navigation.HIGHLIGHT_RETROSPECTION_ID
import com.depromeet.team5.core.navigation.graphkey.Home
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.feedback.screen.AiFeedbackRoute
import kotlinx.serialization.Serializable

@Serializable
data class Feedback(
    val retrospectionId: Int? = null,
)

fun NavGraphBuilder.feedbackScreen(
    navController: NavController,
    onShowErrorToast: (Throwable) -> Unit,
) {
    composable<Feedback> { entry ->

        val parentEntry = remember(entry) {
            navController.getBackStackEntry(Home)
        }

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        val feedbackArgs = entry.toRoute<Feedback>()
        val retrospectionId = feedbackArgs.retrospectionId

        AiFeedbackRoute(
            requestViewModel = sharedViewModel,
            retrospectionId = entry.toRoute<Feedback>().retrospectionId,
            onBack = navController::popBackStack,
            onCompleteClick = if (retrospectionId == null) {
                { id ->
                    navController.getBackStackEntry(Home)
                        .savedStateHandle[HIGHLIGHT_RETROSPECTION_ID] = id
                    navController.popBackStack(Home, inclusive = false)
                }
            } else {
                { _ ->
                    navController.popBackStack()
                }
            },
            onShowErrorToast = onShowErrorToast
        )
    }
}

fun NavController.navigateToFeedback(retrospectionId: Int? = null) {
    navigate(Feedback(retrospectionId))
}