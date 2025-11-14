package com.depromeet.team5

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.depromeet.team5.core.navigation.Splash
import com.depromeet.team5.core.navigation.graphkey.Home
import com.depromeet.team5.feature.reasons.imageDetailNavigation
import com.depromeet.team5.feature.reasons.navigateToImageDetail
import com.depromeet.team5.feature.reasons.navigateToReason
import com.depromeet.team5.feature.reasons.navigateToRetrospectionDetail
import com.depromeet.team5.feature.reasons.reasonNavigation
import com.depromeet.team5.feature.reasons.retrospectionDetailNavigation
import com.depromeet.team5.features.feedback.navigation.feedbackScreen
import com.depromeet.team5.features.feedback.navigation.navigateToFeedback
import com.depromeet.team5.features.home.homeScreen
import com.depromeet.team5.features.login.LoginGraph
import com.depromeet.team5.features.login.loginGraph
import com.depromeet.team5.features.retrospect.screen.navigateToRetrospect
import com.depromeet.team5.features.retrospect.screen.retrospectScreen
import com.depromeet.team5.features.search.navigateToSearch
import com.depromeet.team5.features.search.searchScreen
import com.depromeet.team5.graph.navigatePrincipleGraph
import com.depromeet.team5.graph.principleGraph
import com.depromeet.team5.splash.splashScreen

@Composable
fun HedgeNavHost(
    modifier: Modifier = Modifier,
    onLoginKakao: suspend () -> Result<Pair<String, String>>,
    onShowErrorToast: (Throwable) -> Unit,
    onShowToast: (String) -> Unit,
    onShowNoIconToast: (String) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = modifier
    ) {
        splashScreen(
            navigateToLogin = {
                navController.navigate(LoginGraph) {
                    popUpTo(Splash) { inclusive = true }
                }
            }
        )

        loginGraph(
            navController = navController,
            onClickBack = navController::popBackStack,
            onClickNext = {
                navController.navigate(Home) {
                    popUpTo(LoginGraph) { inclusive = true }
                    launchSingleTop = true
                }
            },
            onLoginKakao = onLoginKakao
        )

        homeScreen(
            navController = navController,
            onBuyClick = { navController.navigateToSearch() },
            onSellClick = { navController.navigateToSearch() },
            onClickRetrospectionDetail = navController::navigateToRetrospectionDetail,
            onClickPrincipleDetail = { groupId, path ->
                navController.navigatePrincipleGraph(groupId, path)
            },
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
            onClickedConfirmButton = { navController.navigateToReason() },
            onShowErrorToast = onShowErrorToast
        )

        principleGraph(
            navController = navController,
            onShowErrorToast = onShowErrorToast,
            onShowToast = onShowToast,
            onShowNoIconToast = onShowNoIconToast
        )

        reasonNavigation(
            navController = navController,
            onClickBack = navController::popBackStack,
            onClickDone = navController::navigateToFeedback,
            onClickImage = navController::navigateToImageDetail
        )

        retrospectionDetailNavigation(
            onClickBack = navController::popBackStack,
            onClickFeedback = { retrospectionId ->
                //todo navigateToFeedback
            },
            onClickImage = navController::navigateToImageDetail,
            onShowToast = onShowToast,
            onShowErrorToast = onShowErrorToast,
        )

        imageDetailNavigation(
            navController = navController,
            onClickBack = navController::popBackStack,
        )

        feedbackScreen(
            navController = navController,
            onRemoveClick = { navController.popBackStack(Home, inclusive = false) }
        )
    }
}
