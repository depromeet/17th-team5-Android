package com.depromeet.team5

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.depromeet.team5.features.search.Search
import com.depromeet.team5.features.search.searchGraph

@Composable
fun HedgeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
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