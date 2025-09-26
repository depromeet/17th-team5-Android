package com.depromeet.team5.features.feedback.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.features.feedback.screen.FeedbackRoute
import kotlinx.serialization.Serializable


@Serializable
class FeedbackParams(
    val retrospectionId: Int,
    val companyName: String,
    val price: Long,
    val stock: Int,
    val date: String
)

fun NavController.navigateFeedback(
    retrospectionId: Int,
    companyName: String,
    price: Long,
    stock: Int,
    date: String
) = navigate(
    route = FeedbackParams(
        retrospectionId = retrospectionId,
        companyName = companyName,
        price = price,
        stock = stock,
        date = date
    )
)

fun NavGraphBuilder.feedbackScreen(onRemoveClick: () -> Unit) {
    composable<FeedbackParams> { entry ->
        FeedbackRoute(
            onRemoveClick = onRemoveClick
        )
    }
}
