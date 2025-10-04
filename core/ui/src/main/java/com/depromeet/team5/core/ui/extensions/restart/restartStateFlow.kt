package com.depromeet.team5.core.ui.extensions.restart

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

class RestartSharingStarted(
    private val sharingStarted: SharingStarted
) : SharingStarted {

    private val retryFlow = MutableSharedFlow<SharingCommand>(extraBufferCapacity = 2)


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun command(subscriptionCount: StateFlow<Int>): Flow<SharingCommand> =
        merge(sharingStarted.command(subscriptionCount), retryFlow)


    fun retry() {
        retryFlow.tryEmit(SharingCommand.STOP_AND_RESET_REPLAY_CACHE)
        retryFlow.tryEmit(SharingCommand.START)
    }
}

fun <T> Flow<T>.restartStateIn(
    scope: CoroutineScope,
    sharedStarted: SharingStarted,
    initValue: T
): StateFlow<T> {
    val restartSharingStarted = RestartSharingStarted(sharedStarted)
    val stateFlow = stateIn(
        scope = scope,
        started = restartSharingStarted,
        initialValue = initValue
    )

    return object : RestartableStateFlow<T>, StateFlow<T> by stateFlow {
        override fun restart() = restartSharingStarted.retry()
    }
}
