package com.depromeet.team5.core.domain.model


data class SearchEntity(
    val code: String,
    val message: String,
    val data: List<SearchInfoEntity>
)

data class SearchInfoEntity(
    val market: String,
    val symbol: String,
    val title: String
)
