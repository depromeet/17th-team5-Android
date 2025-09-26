package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.SearchInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.SearchRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper


data class SearchResponse(
    val code: String,
    val message: String,
    val data: List<SearchInfoResponse>
) : RetrofitMapper<SearchRemoteData> {

    override fun toRemoteData(): SearchRemoteData = SearchRemoteData(
        code = code,
        message = message,
        data = data.map { it.toRemoteData() }
    )
}

data class SearchInfoResponse(
    val market: String,
    val code: String,
    val companyName: String
) : RetrofitMapper<SearchInfoRemoteData> {
    override fun toRemoteData(): SearchInfoRemoteData = SearchInfoRemoteData(
        market = market,
        symbol = code,
        title = companyName
    )
}
