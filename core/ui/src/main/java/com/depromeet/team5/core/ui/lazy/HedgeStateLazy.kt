package com.depromeet.team5.core.ui.lazy

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


class HedgeStateLazy<T : Any?>(
    private val initValue: T
) : ReadOnlyProperty<Any?, HedgeState<T>> {

    private val state: HedgeState<T> by lazy {
        HedgeState(initValue)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): HedgeState<T> = state
}


fun <T> hedgeState(initValue: T) = HedgeStateLazy(initValue)
