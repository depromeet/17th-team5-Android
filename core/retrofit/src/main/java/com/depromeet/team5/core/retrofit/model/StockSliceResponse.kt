package com.depromeet.team5.core.retrofit.model

import kotlinx.serialization.Serializable
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import com.depromeet.team5.core.remotedatasource.model.StockSliceRemoteData
import com.depromeet.team5.core.remotedatasource.model.StockInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.StockSlicePageRemoteData

@Serializable
data class StockSliceResponse(
    val code: String,
    val message: String,
    val data: StockSlicePageResponse
) : RetrofitMapper<StockSliceRemoteData> {

    override fun toRemoteData(): StockSliceRemoteData = StockSliceRemoteData(
        code = code,
        message = message,
        data = data.toRemoteData()
    )
}

@Serializable
data class StockSlicePageResponse(
    val content: List<StockInfoResponse>,
    val nextCursor: String?
) : RetrofitMapper<StockSlicePageRemoteData> {

    override fun toRemoteData(): StockSlicePageRemoteData = StockSlicePageRemoteData(
        content = content.map { it.toRemoteData() },
        nextCursor = nextCursor
    )
}

@Serializable
data class StockInfoResponse(
    val market: String,
    val code: String,
    val companyName: String,
    val logo: String?
) : RetrofitMapper<StockInfoRemoteData> {

    override fun toRemoteData(): StockInfoRemoteData = StockInfoRemoteData(
        market = market,
        symbol = code,
        companyName = companyName,
        logo = logo
    )
}
