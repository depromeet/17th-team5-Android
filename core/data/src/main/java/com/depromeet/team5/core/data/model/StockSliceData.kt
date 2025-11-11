package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.domain.model.StockSlice
import com.depromeet.team5.core.domain.model.StockSlicePage
import com.depromeet.team5.core.domain.model.StockInfo
import com.depromeet.team5.core.data.mapper.DataMapper

data class StockSliceData(
    val code: String,
    val message: String,
    val data: StockSlicePageData
) : DataMapper<StockSlice> {

    override fun toDomain(): StockSlice = StockSlice(
        code = code,
        message = message,
        data = data.toDomain()
    )
}

data class StockSlicePageData(
    val content: List<StockInfoData>,
    val nextCursor: String?
) : DataMapper<StockSlicePage> {

    override fun toDomain(): StockSlicePage = StockSlicePage(
        content = content.map { it.toDomain() },
        nextCursor = nextCursor
    )
}

data class StockInfoData(
    val market: String,
    val symbol: String,
    val companyName: String,
    val logo: String?
) : DataMapper<StockInfo> {

    override fun toDomain(): StockInfo = StockInfo(
        market = market,
        symbol = symbol,
        companyName = companyName,
        logo = logo
    )
}
