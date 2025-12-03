package com.depromeet.team5.features.feedback.event


sealed class Event {

    data class ShowErrorToast(val throwable: Throwable) : Event()
}
