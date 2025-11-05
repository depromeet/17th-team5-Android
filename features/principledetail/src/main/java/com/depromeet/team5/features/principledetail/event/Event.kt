package com.depromeet.team5.features.principledetail.event


sealed class PrincipleDetailEvent {

    object Finish : PrincipleDetailEvent()

    data class ShowErrorToast(val throwable: Throwable) : PrincipleDetailEvent()

    object None : PrincipleDetailEvent()
}
