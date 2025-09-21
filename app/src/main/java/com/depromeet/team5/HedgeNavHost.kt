package com.depromeet.team5

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HedgeNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Search,
        modifier = modifier
    ){
        searchGraph(
            onBackClick = {navController.popBackStack()}
        )
    }
}