package com.depromeet.team5

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.depromeet.team5.features.search.Search
import com.depromeet.team5.features.search.searchGraph

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