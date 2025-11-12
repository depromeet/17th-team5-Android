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
import com.depromeet.team5.features.login.LoginGraph
import com.depromeet.team5.features.login.loginGraph
import com.depromeet.team5.features.principlegroupdetail.navigation.principleGroupDetail
import com.depromeet.team5.features.retrospect.screen.navigateToRetrospect
import com.depromeet.team5.features.retrospect.screen.retrospectScreen
import com.depromeet.team5.features.search.navigateToSearch
import com.depromeet.team5.features.search.searchScreen
import com.depromeet.team5.graph.navigatePrincipleGraph
import com.depromeet.team5.graph.principleGraph

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
        startDestination = LoginGraph,
        modifier = modifier
    ) {
        loginGraph(
            navController = navController,
            onClickBack = navController::popBackStack,
            onClickNext = { navController.navigate(Home) },
            onLoginKakao = onLoginKakao
        )

        homeScreen(
            navController = navController,
            onBuyClick = { navController.navigateToSearch() },
            onSellClick = { navController.navigateToSearch() },
            onClickRetrospectionDetail = { },
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
            onClickedConfirmButton = { navController.navigateToReasonGraph() },
            onShowErrorToast = onShowErrorToast
        )

        principleGraph(
            navController = navController,
            onShowErrorToast = onShowErrorToast,
            onShowToast = onShowToast,
            onShowNoIconToast = onShowNoIconToast
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
