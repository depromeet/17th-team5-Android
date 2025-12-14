package com.depromeet.team5.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.depromeet.team5.core.ui.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.zip

@Composable
fun SplashRoute(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.eventBus
            .receiveAsFlow()
            .zip(flow {
                delay(1500)
                emit(Unit)
            }) { event, _ -> event }
            .collect { event ->
                when (event) {
                    NavigationType.HOME -> navigateToHome()
                    NavigationType.LOGIN -> navigateToLogin()
                }
            }

        navigateToHome()
    }
    SplashScreen(modifier = modifier)
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to Color(0xFF29F980).copy(alpha = 0.16f),
                        0.6f to Color(0xFF29F980).copy(alpha = 0f),
                        1.0f to Color(0xFF29F980).copy(alpha = 0f)
                    )
                )
            )
            .padding(top = 271.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_hedge_logo),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}