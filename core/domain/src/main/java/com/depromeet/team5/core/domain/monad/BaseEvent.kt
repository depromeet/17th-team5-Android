package com.depromeet.team5.core.domain.monad


sealed interface BaseEvent<out T> {

    data class Finish<T>(val result: T) : BaseEvent<T>

    data class Error(val throwable: Throwable?) : BaseEvent<Nothing>

    data class ShowToast(val message: String?) : BaseEvent<Nothing>

}
