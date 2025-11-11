package com.depromeet.team5

import android.app.Application
import com.depromeet.team5.BuildConfig.KAKAO_NATIVE_APP_KEY
import com.depromeet.team5.core.logger.Logger
import com.depromeet.team5.core.logger.annotation.BuildVariant
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class DepromeetApplication : Application() {

    @Inject
    lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()
        initKakaoSdk()

        if (BuildConfig.DEBUG) {
            logger.plant(BuildVariant.DEBUG)
        } else {
            logger.plant(BuildVariant.RELEASE)
        }
    }

    private fun initKakaoSdk(){
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)
    }
}
