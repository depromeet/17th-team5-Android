package com.depromeet.team5.core.domain.model

data class StockSlice(
    val code: String,
    val message: String,
    val data: StockSlicePage
)

data class StockSlicePage(
    val content: List<StockInfo>,
    val nextCursor: String?
)

data class StockInfo(
    val market: String,
    val symbol: String,
    val companyName: String,
    val logo: String?
)
