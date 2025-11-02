package com.depromeet.team5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.depromeet.team5.core.logger.Logger
import com.depromeet.team5.ui.theme.DepromeetTheme
import dagger.hilt.android.AndroidEntryPoint
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
                HedgeNavHost(
                    onShowErrorToast = { throwable ->
                        logger.LogAndToast(throwable)
                    }
                )
            }
        }
    }
}
