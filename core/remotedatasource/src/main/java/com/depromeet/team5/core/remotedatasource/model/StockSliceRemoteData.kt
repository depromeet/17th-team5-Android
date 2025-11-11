package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.StockSliceData
import com.depromeet.team5.core.data.model.StockSlicePageData
import com.depromeet.team5.core.data.model.StockInfoData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

data class StockSliceRemoteData(
    val code: String,
    val message: String,
    val data: StockSlicePageRemoteData
) : RemoteDataMapper<StockSliceData> {

    override fun toData(): StockSliceData = StockSliceData(
        code = code,
        message = message,
        data = data.toData()
    )
}

data class StockSlicePageRemoteData(
    val content: List<StockInfoRemoteData>,
    val nextCursor: String?
) : RemoteDataMapper<StockSlicePageData> {

    override fun toData(): StockSlicePageData = StockSlicePageData(
        content = content.map { it.toData() },
        nextCursor = nextCursor
    )
}

data class StockInfoRemoteData(
    val market: String,
    val symbol: String,
    val companyName: String,
    val logo: String?
) : RemoteDataMapper<StockInfoData> {

    override fun toData(): StockInfoData = StockInfoData(
        market = market,
        symbol = symbol,
        companyName = companyName,
        logo = logo
    )
}
