package com.depromeet.team5.features.principlegroupdetail.state


sealed class ModalUiState {

    data class ForPrincipleGroup(
        val id: Int
    ) : ModalUiState()

    data class ForPrinciple(
        val id: Int
    ) : ModalUiState()

    object NotShown : ModalUiState()

}
