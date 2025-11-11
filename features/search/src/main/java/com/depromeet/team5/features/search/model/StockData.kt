package com.depromeet.team5.features.search.model

data class StockData(
    val symbol: String,
    val stockName: String,
    val market: String,
    val stockImageUrl: String? = null
)
