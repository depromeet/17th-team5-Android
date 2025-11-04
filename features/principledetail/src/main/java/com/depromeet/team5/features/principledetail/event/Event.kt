package com.depromeet.team5.features.principledetail.event


sealed class PrincipleDetailEvent {

    object Finish : PrincipleDetailEvent()

    class ShowErrorToast : PrincipleDetailEvent()

    object None : PrincipleDetailEvent()
}
