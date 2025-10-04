package com.depromeet.team5.core.ui.lazy

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


class HedgeState<T : Any?>(
    private val initValue: T
) {
    private val _stateFlow: MutableStateFlow<T> = MutableStateFlow(initValue)
    val stateFlow: StateFlow<T> = _stateFlow.asStateFlow()


    suspend fun emit(value: T) {
        _stateFlow.emit(value)
    }

    fun tryEmit(value: T) = _stateFlow.tryEmit(value)

    fun update(onUpdate: (T) -> T) {
        _stateFlow.update(onUpdate)
    }
}

class HedgeStateLazy<T : Any?>(
    private val initValue: T
) : ReadOnlyProperty<Any?, HedgeState<T>> {

    private val state: HedgeState<T> by lazy {
        HedgeState(initValue)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): HedgeState<T> = state
}


fun <T> hedgeState(initValue: T) = HedgeStateLazy(initValue)
