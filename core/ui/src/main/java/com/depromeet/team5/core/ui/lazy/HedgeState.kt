package com.depromeet.team5.core.ui.lazy

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class HedgeState<T : Any?>(
    private val initValue: T
) {
    private val _stateFlow: MutableStateFlow<T> = MutableStateFlow(initValue)
    val stateFlow: StateFlow<T> = _stateFlow.asStateFlow()

    /** Shotcut property of [stateFlow.value] */
    val value: T
        get() = stateFlow.value

    suspend fun emit(value: T) {
        _stateFlow.emit(value)
    }

    fun tryEmit(value: T) = _stateFlow.tryEmit(value)

    fun update(onUpdate: (T) -> T) {
        _stateFlow.update(onUpdate)
    }
}
