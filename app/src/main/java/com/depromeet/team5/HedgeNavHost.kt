package com.depromeet.team5

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.depromeet.team5.feature.reasons.navigateToReasonGraph
import com.depromeet.team5.feature.reasons.reasonGraph
import com.depromeet.team5.features.feedback.navigation.feedbackScreen
import com.depromeet.team5.features.feedback.navigation.navigateToFeedback
import com.depromeet.team5.features.home.Home
import com.depromeet.team5.features.home.homeScreen
import com.depromeet.team5.features.principle.navigation.navigateToPrinciple
import com.depromeet.team5.features.principle.navigation.principleScreen
import com.depromeet.team5.features.principledetail.navigation.principleDetail
import com.depromeet.team5.features.retrospect.screen.navigateToRetrospect
import com.depromeet.team5.features.retrospect.screen.retrospectScreen
import com.depromeet.team5.features.search.navigateToSearch
import com.depromeet.team5.features.search.searchScreen

@Composable
fun HedgeNavHost(
    modifier: Modifier = Modifier,
    onShowErrorToast: (Throwable) -> Unit,
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
            onSellClick = { navController.navigateToSearch() },
            onClickRetrospectionDetail = { },
            onClickPrincipleDetail = { },
            onClickCreatePrinciple = { },
            onShowErrorToast = onShowErrorToast
        )

        searchScreen(
            navController = navController,
            onBackClick = { navController.popBackStack() },
            onItemClick = { navController.navigateToRetrospect() }
        )

        retrospectScreen(
            navController = navController,
            onBackPressed = { navController.popBackStack() },
            onClickedConfirmButton = { navController.navigateToReasonGraph() },
            onShowErrorToast = onShowErrorToast
        )

        principleScreen(
            navController = navController,
            onBackPressed = { navController.popBackStack() },
            onClickNext = { navController.navigateToReasonGraph() }
        )

        principleDetail(
            onBackPressed = navController::popBackStack,
            onShowErrorToast = onShowErrorToast
        )

        reasonGraph(
            navController = navController,
            onClickBack = navController::popBackStack,
            onClickDone = navController::navigateToFeedback,
        )

        feedbackScreen(
            navController = navController,
            onRemoveClick = { navController.popBackStack(Home, inclusive = false) }
        )
    }
}