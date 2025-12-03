package com.depromeet.team5.feature.reasons

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.navigation.IS_RETROSPECTION_UPDATED
import kotlinx.serialization.Serializable

@Serializable
data class RetrospectionDetail(
    val retrospectionId: Int,
    val orderType: OrderType
)

fun NavGraphBuilder.retrospectionDetailNavigation(
    navController: NavController,
    onClickFeedback: (Int, OrderType) -> Unit,
    onClickImage: (ImageDetail) -> Unit,
    onShowToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
) {
    composable<RetrospectionDetail> { backStackEntry ->
        val retrospectionDetail = backStackEntry.toRoute<RetrospectionDetail>()

        RetrospectionDetailRoute(
            onClickBack = { isUpdated ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(IS_RETROSPECTION_UPDATED, isUpdated)

                navController.popBackStack()
            },
            onClickFeedback = { id ->
                onClickFeedback(id, retrospectionDetail.orderType)
            },
            onClickImage = onClickImage,
            onShowToast = onShowToast,
            onShowErrorToast = onShowErrorToast,
        )
    }
}

fun NavController.navigateToRetrospectionDetail(retrospectionId: Int, orderType: OrderType) {
    navigate(RetrospectionDetail(retrospectionId, orderType))
}