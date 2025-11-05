package com.depromeet.team5

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.depromeet.team5.feature.reasons.navigateToReason
import com.depromeet.team5.feature.reasons.reasonScreen
import com.depromeet.team5.features.feedback.navigation.feedbackScreen
import com.depromeet.team5.features.feedback.navigation.navigateToFeedback
import com.depromeet.team5.features.home.Home
import com.depromeet.team5.features.home.homeScreen
import com.depromeet.team5.features.principle.navigation.principleScreen
import com.depromeet.team5.features.retrospect.screen.Retrospect
import com.depromeet.team5.features.retrospect.screen.navigateToRetrospect
import com.depromeet.team5.features.retrospect.screen.retrospectScreen
import com.depromeet.team5.features.search.navigateToSearch
import com.depromeet.team5.features.search.searchScreen

@Composable
fun HedgeNavHost(
    modifier: Modifier = Modifier,
    onShowErrorToast: (Throwable) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ) {
        homeScreen(
            navController = navController,
            onBuyClick = { navController.navigateToSearch() },
            onSellClick = { navController.navigateToSearch() }
        )

        searchScreen(
            navController = navController,
            onBackClick = { navController.popBackStack() },
            onItemClick = { navController.navigateToRetrospect() }
        )

        retrospectScreen(
            navController = navController,
            onBackPressed = { navController.popBackStack() },
            onClickedConfirmButton = { navController.navigateToReason() },
            onShowErrorToast = onShowErrorToast
        )

        principleScreen(
            navController = navController,
            onBackPressed = { navController.popBackStack() },
            onClickNext = { navController.navigateToReason() }
        )

        reasonScreen(
            navController = navController,
            onClickBack = navController::popBackStack,
            onClickDone = navController::navigateToFeedback,
            onClickEditTradeInfo = { navController.popBackStack(route = Retrospect, inclusive = false) }
        )

        feedbackScreen(
            navController = navController,
            onRemoveClick = { navController.popBackStack(Home, inclusive = false) }
        )
    }
}