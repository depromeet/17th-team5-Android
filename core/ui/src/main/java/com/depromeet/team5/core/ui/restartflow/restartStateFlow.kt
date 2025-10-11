package com.depromeet.team5.core.ui.restartflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn


interface RestartableStateFlow<T> : StateFlow<T> {

    fun restart()
}

private class RestartSharingStarted(
    private val sharingStarted: SharingStarted
) : SharingStarted {

    private val restartFlow = MutableSharedFlow<SharingCommand>(extraBufferCapacity = 2)


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> =
        merge(sharingStarted.command(subscriptionCount), restartFlow)


    fun restart() {
        restartFlow.tryEmit(SharingCommand.STOP_AND_RESET_REPLAY_CACHE)
        restartFlow.tryEmit(SharingCommand.START)
    }
}

fun <T> Flow<T>.restartStateIn(
    scope: CoroutineScope,
    started: SharingStarted,
    initialValue: T
): RestartableStateFlow<T> {
    val restartSharingStarted = RestartSharingStarted(started)
    val stateFlow = stateIn(
        scope = scope,
        started = restartSharingStarted,
        initialValue = initialValue
    )

    return object : RestartableStateFlow<T>, StateFlow<T> by stateFlow {
        override fun restart() = restartSharingStarted.restart()
    }
}
