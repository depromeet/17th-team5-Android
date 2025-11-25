package com.depromeet.team5.features.feedback.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.navigation.HIGHLIGHT_RETROSPECTION_ID
import com.depromeet.team5.core.navigation.graphkey.Home
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.feedback.screen.AiFeedbackRoute
import kotlinx.serialization.Serializable

@Serializable
data class Feedback(
    val retrospectionId: Int? = null,
    val orderType: OrderType? = null
)

fun NavGraphBuilder.feedbackScreen(
    navController: NavController,
    onRemoveClick: () -> Unit,
    onNavigatedToNewPrinciple: (Int, List<String>) -> Unit,
    onShowToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit
) {
    composable<Feedback> { entry ->
        val parentEntry = remember(entry) {
            navController.getBackStackEntry(Home)
        }

        val feedback = entry.toRoute<Feedback>()

        val sharedViewModel: RequestViewModel = viewModel(viewModelStoreOwner = parentEntry)

        if (feedback.orderType != null) {
            sharedViewModel.request = sharedViewModel.request.copy(orderType = feedback.orderType)
        }

        AiFeedbackRoute(
            requestViewModel = sharedViewModel,
            retrospectionId = entry.toRoute<Feedback>().retrospectionId,
            onBack = navController::popBackStack,
            onCompleteClick = { id ->
                navController.getBackStackEntry(Home)
                    .savedStateHandle[HIGHLIGHT_RETROSPECTION_ID] = id
                navController.popBackStack(Home, inclusive = false)
            },
//            onRemoveClick = onRemoveClick,
            onClickedAddPrinciples = onNavigatedToNewPrinciple,
            onShowToast = onShowToast,
            onShowErrorToast = onShowErrorToast
        )
    }
}

fun NavController.navigateToFeedback(
    retrospectionId: Int? = null,
    orderType: OrderType? = null
) {
    navigate(Feedback(retrospectionId, orderType))
}