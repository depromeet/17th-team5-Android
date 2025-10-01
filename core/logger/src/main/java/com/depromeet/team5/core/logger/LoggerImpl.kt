package com.depromeet.team5.core.logger

import android.content.Context
import android.widget.Toast
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
            BuildVariant.RELEASE -> {}//todo crashlytics와 연결하기
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

    override fun logAndToast(throwable: Throwable) {
        //todo custom throwable을 구분하여 message 받아 처리하기

        e(throwable)

        Toast.makeText(
            context,
            context.resources.getString(R.string.error_default_message),
            Toast.LENGTH_SHORT
        ).show()
    }

}
