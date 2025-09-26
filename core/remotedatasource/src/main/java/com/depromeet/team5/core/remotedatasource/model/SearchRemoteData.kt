package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.SearchData
import com.depromeet.team5.core.data.model.SearchInfoData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper


data class SearchRemoteData(
    val code: String,
    val message: String,
    val data: List<SearchInfoRemoteData>
) : RemoteDataMapper<SearchData> {

    override fun toData(): SearchData = SearchData(
        code = code,
        message = message,
        data = data.map { it.toData() }
    )
}

data class SearchInfoRemoteData(
    val market: String,
    val symbol: String,
    val title: String
) : RemoteDataMapper<SearchInfoData> {

    override fun toData(): SearchInfoData = SearchInfoData(
        market = market,
        symbol = symbol,
        title = title
    )
}
