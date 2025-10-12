package com.depromeet.team5.core.domain.model


data class Search(
    val code: String,
    val message: String,
    val data: List<SearchInfo>
)

data class SearchInfo(
    val market: String,
    val symbol: String,
    val title: String
)
