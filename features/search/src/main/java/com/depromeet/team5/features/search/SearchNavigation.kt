package com.depromeet.team5.features.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Search

fun NavGraphBuilder.searchGraph(
    onBackClick: () -> Unit
){
    composable<Search>{
        SearchRoute(
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToSearch(){
    navigate(Search)
}