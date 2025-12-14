package com.depromeet.team5.core.retrofit

import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManager @Inject constructor() {

    val loginEvent = Channel<Unit>()


}
