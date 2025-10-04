package com.depromeet.team5.core.logger

import androidx.compose.runtime.Composable
import com.depromeet.team5.core.logger.annotation.BuildVariant


interface Logger {

    fun plant(variant: BuildVariant)

    fun i(message: String, vararg args: Any? = emptyArray())

    fun i(throwable: Throwable)

    fun v(message: String, vararg args: Any? = emptyArray())

    fun v(throwable: Throwable)

    fun d(message: String, vararg args: Any? = emptyArray())

    fun d(throwable: Throwable)

    fun w(message: String, vararg args: Any? = emptyArray())

    fun w(throwable: Throwable)

    fun e(message: String, vararg args: Any? = emptyArray())

    fun e(throwable: Throwable)

    @Composable
    fun LogAndToast(throwable: Throwable)
}
