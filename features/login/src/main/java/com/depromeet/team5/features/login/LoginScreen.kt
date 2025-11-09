package com.depromeet.team5.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
) {
    LoginScreen(
        modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 227.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_hedge_logo),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Text(
                text = stringResource(R.string.login_service_description),
                style = HedgeTypography.Body1.SemiBold,
                color = HedgeColor.Text.Primary,
                modifier = Modifier.padding(top = 20.dp),
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .background(color = Color(0xFFFEE500), shape = RoundedCornerShape(18.dp))
                .align(Alignment.BottomCenter)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_kakao_logo),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.padding(end = 10.dp)
            )

            Text(
                text = stringResource(R.string.login_kakao),
                style = HedgeTypography.Body1.SemiBold,
                color = Color(0xFF191919),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}