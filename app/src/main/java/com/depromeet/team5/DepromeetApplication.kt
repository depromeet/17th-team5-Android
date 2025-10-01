package com.depromeet.team5

import android.app.Application
import com.depromeet.team5.core.logger.Logger
import com.depromeet.team5.core.logger.annotation.BuildVariant
import com.depromeet.team5.core.retrofit.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class DepromeetApplication : Application() {

    @Inject
    lateinit var logger: Logger


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            logger.plant(BuildVariant.DEBUG)
        } else {
            logger.plant(BuildVariant.RELEASE)
        }
    }
}
