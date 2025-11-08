package com.depromeet.team5.features.principlemodification.event


sealed class Event {
    data class ShowErrorToast(
        val throwable: Throwable
    ) : Event()

    object Complete : Event()
}
