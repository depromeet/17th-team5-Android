package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.SearchEntity
import com.depromeet.team5.core.domain.model.SearchInfoEntity


data class SearchData(
    val code: String,
    val message: String,
    val data: List<SearchInfoData>
) : DataMapper<SearchEntity> {

    override fun toDomain(): SearchEntity = SearchEntity(
        code = code,
        message = message,
        data = data.map { it.toDomain() }
    )
}

data class SearchInfoData(
    val market: String,
    val symbol: String,
    val title: String
) : DataMapper<SearchInfoEntity> {

    override fun toDomain(): SearchInfoEntity = SearchInfoEntity(
        market = market,
        symbol = symbol,
        title = title
    )
}
