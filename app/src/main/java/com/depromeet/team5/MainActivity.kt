package com.depromeet.team5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.depromeet.team5.core.designsystem.component.HedgeToast
import com.depromeet.team5.core.designsystem.component.HedgeToastState
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.logger.Logger
import com.depromeet.team5.ui.theme.DepromeetTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DepromeetTheme {
                val context = LocalContext.current
                val scope = rememberCoroutineScope()

                val hedgeErrorToastState = remember { HedgeToastState() }
                val hedgeRegularToastState = remember { HedgeToastState() }
                val hedgeNoIconToastState = remember { HedgeToastState() }

                HedgeNavHost(
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
