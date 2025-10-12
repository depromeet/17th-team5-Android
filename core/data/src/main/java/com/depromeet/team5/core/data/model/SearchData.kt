package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.Search
import com.depromeet.team5.core.domain.model.SearchInfo


data class SearchData(
    val code: String,
    val message: String,
    val data: List<SearchInfoData>
) : DataMapper<Search> {

    override fun toDomain(): Search = Search(
        code = code,
        message = message,
        data = data.map { it.toDomain() }
    )
}

data class SearchInfoData(
    val market: String,
    val symbol: String,
    val title: String
) : DataMapper<SearchInfo> {

    override fun toDomain(): SearchInfo = SearchInfo(
        market = market,
        symbol = symbol,
        title = title
    )
}
