package com.depromeet.team5.features.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Setting

fun NavGraphBuilder.settingScreen(
    navController: NavController,
    onBackClick: () -> Unit
){
    composable<Setting>{
        SettingRoute(
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToSetting(){
    navigate(Setting)
}
