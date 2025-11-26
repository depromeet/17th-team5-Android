package com.depromeet.team5.features.newprinciples.screen.state


data class UiState(
    val page: Int,
    val isLastPage: Boolean,
    val groupName: String,
    val title: String,
    val newPrinciples: List<String>
) {

    companion object {

        val EMPTY = UiState(
            page = 0,
            isLastPage = false,
            groupName = "",
            title = "",
            newPrinciples = emptyList()
        )
    }
}
