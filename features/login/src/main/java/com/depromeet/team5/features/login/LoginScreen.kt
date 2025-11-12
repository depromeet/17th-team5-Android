package com.depromeet.team5.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.SocialLogin
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.ui.extensions.baseCollect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


@Composable
fun LoginRoute(
    navigateToAgreements: () -> Unit,
    onLoginKakao: suspend () -> Result<Pair<String, String>>,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val loginState by viewModel.socialLoginUiState.collectAsStateWithLifecycle()

    LaunchedEffect(loginState) {
        if (loginState is HedgeUiState.Success<SocialLogin>){
            navigateToAgreements()
            viewModel.consumeLoginResult()
        }
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .flatMapLatest {
                    flow {
                        emit(onLoginKakao())
                    }
                }
                .baseCollect(
                    onSuccess = { viewModel.authorize(it) },
                    onError = { viewModel.authorize(Result.failure(it)) }
                )
        }
    }

    LoginScreen(
        onClickKakao = { viewModel.loginWithKakao() },
        modifier = modifier
    )
}

@Composable
private fun LoginScreen(
    onClickKakao: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 271.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = HedgeIcon.Logo,
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
                .clip(RoundedCornerShape(18.dp))
                .background(color = Color(0xFFFEE500))
                .clickable { onClickKakao() }
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
    LoginScreen(
        onClickKakao = {}
    )
}