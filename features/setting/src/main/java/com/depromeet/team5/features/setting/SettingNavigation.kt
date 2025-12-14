package com.depromeet.team5.features.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Setting

fun NavGraphBuilder.settingScreen(
    onBackClick: () -> Unit,
    onLogout: () -> Unit
) {
    composable<Setting> {
        SettingRoute(
            onBackClick = onBackClick,
            onLogout = onLogout
        )
    }
}

fun NavController.navigateToSetting() {
    navigate(Setting)
}
