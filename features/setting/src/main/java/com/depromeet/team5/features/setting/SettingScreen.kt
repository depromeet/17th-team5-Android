package com.depromeet.team5.features.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingScreen(
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
private fun SettingScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview(showBackground = true)
@Composable
private fun SettingPreview() {
    SettingScreen(
        onBackClick = {}
    )
}