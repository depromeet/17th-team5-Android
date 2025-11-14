package com.depromeet.team5.features.principlegroupdetail.event

sealed class PrincipleDetailEvent {

    object Finish : PrincipleDetailEvent()

    data class ShowErrorToast(val throwable: Throwable) : PrincipleDetailEvent()

    object FinishAndShowToast : PrincipleDetailEvent()

    object None : PrincipleDetailEvent()
}
