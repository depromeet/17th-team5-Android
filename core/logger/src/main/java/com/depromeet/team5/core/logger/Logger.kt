package com.depromeet.team5.core.logger

import com.depromeet.team5.core.logger.annotation.BuildVariant
import timber.log.Timber


object Logger {

    fun plant(variant: BuildVariant) {
        when (variant) {
            BuildVariant.DEBUG -> Timber.plant(Timber.DebugTree())
            BuildVariant.RELEASE -> Timber.plant(CrashlyticsTree())
        }
    }

    fun i(message: String, vararg args: Any?) {
        Timber.i(message, args)
    }

    fun i(throwable: Throwable) {
        Timber.i(throwable)
    }

    fun v(message: String, vararg args: Any?) {
        Timber.v(message, args)
    }

    fun v(throwable: Throwable) {
        Timber.v(throwable)
    }

    fun d(message: String, vararg args: Any?) {
        Timber.d(message, args)
    }

    fun d(throwable: Throwable) {
        Timber.d(throwable)
    }

    fun w(message: String, vararg args: Any?) {
        Timber.w(message, args)
    }

    fun w(throwable: Throwable) {
        Timber.w(throwable)
    }

    fun e(message: String, vararg args: Any?) {
        Timber.e(message, args)
    }

    fun e(throwable: Throwable) {
        Timber.e(throwable)
    }

}
