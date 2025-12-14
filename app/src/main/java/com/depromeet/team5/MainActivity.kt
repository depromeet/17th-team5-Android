package com.depromeet.team5

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.depromeet.team5.core.designsystem.component.HedgeToast
import com.depromeet.team5.core.designsystem.component.HedgeToastState
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.logger.Logger
import com.depromeet.team5.core.retrofit.SessionManager
import com.depromeet.team5.features.login.KakaoAuthCodeManager
import com.depromeet.team5.features.login.navigateToLogin
import com.depromeet.team5.ui.theme.DepromeetTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var kakaoAuthCodeManager: KakaoAuthCodeManager

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(TRANSPARENT, TRANSPARENT))
        setContent {
            DepromeetTheme {
                val context = LocalContext.current
                val lifecycleOwner = LocalLifecycleOwner.current
                val scope = rememberCoroutineScope()

                val navController = rememberNavController()

                val hedgeErrorToastState = remember { HedgeToastState() }
                val hedgeRegularToastState = remember { HedgeToastState() }
                val hedgeNoIconToastState = remember { HedgeToastState() }

                LaunchedEffect(Unit) {
                    sessionManager.loginEvent
                        .receiveAsFlow()
                        .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                        .collect {
                            navController.navigateToLogin()
                        }
                }

                HedgeNavHost(
                    navController = navController,
                    onShowErrorToast = { throwable ->
                        scope.launch {
                            logger.e(throwable)

                            hedgeErrorToastState.show(
                                text = context.getString(R.string.error_message)
                            )
                        }
                    },
                    onShowToast = { message ->
                        scope.launch {
                            hedgeRegularToastState.show(
                                text = message
                            )
                        }
                    },
                    onShowNoIconToast = { message ->
                        scope.launch {
                            hedgeNoIconToastState.show(
                                text = message
                            )
                        }
                    },
                    onLoginKakao = {
                        kakaoAuthCodeManager.authorize()
                    }
                )

                HedgeToast(
                    hedgeToastState = hedgeErrorToastState
                )

                HedgeToast(
                    hedgeToastState = hedgeRegularToastState,
                    backgroundColor = HedgeColor.Text.Secondary,
                    textColor = HedgeColor.Neutral.BackgroundSecondary,
                    icon = HedgeIcon.ToastCheck
                )

                HedgeToast(
                    hedgeToastState = hedgeNoIconToastState,
                    backgroundColor = HedgeColor.Text.Secondary,
                    textColor = HedgeColor.Neutral.BackgroundSecondary,
                )
            }
        }
    }
}
