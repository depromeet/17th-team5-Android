package com.depromeet.team5.core.domain.monad


sealed interface BaseEvent {

    object Finish : BaseEvent

    data class Error(val throwable: Throwable?) : BaseEvent

    data class ShowToast(val message: String?) : BaseEvent

}
