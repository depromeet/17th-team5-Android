package com.depromeet.team5.core.logger

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.depromeet.team5.core.designsystem.component.HedgeToast
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.logger.annotation.BuildVariant
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject


internal class LoggerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : Logger {


    override fun plant(variant: BuildVariant) {
        when (variant) {
            BuildVariant.DEBUG -> Timber.plant(Timber.DebugTree())
            BuildVariant.RELEASE -> Timber.plant(CrashlyticsTree())
        }
    }

    override fun i(message: String, vararg args: Any?) {
        Timber.i(message, args)
    }

    override fun i(throwable: Throwable) {
        Timber.i(throwable)
    }

    override fun v(message: String, vararg args: Any?) {
        Timber.v(message, args)
    }

    override fun v(throwable: Throwable) {
        Timber.v(throwable)
    }

    override fun d(message: String, vararg args: Any?) {
        Timber.d(message, args)
    }

    override fun d(throwable: Throwable) {
        Timber.d(throwable)
    }

    override fun w(message: String, vararg args: Any?) {
        Timber.w(message, args)
    }

    override fun w(throwable: Throwable) {
        Timber.w(throwable)
    }

    override fun e(message: String, vararg args: Any?) {
        Timber.e(message, args)
    }

    override fun e(throwable: Throwable) {
        Timber.e(throwable)
    }

    @Composable
    override fun LogAndToast(throwable: Throwable) {
        e(throwable)

        HedgeToast(
            text = context.resources.getString(R.string.error_default_message),
            icon = HedgeIcon.Error,
            duration = Toast.LENGTH_SHORT
        )
    }
}
