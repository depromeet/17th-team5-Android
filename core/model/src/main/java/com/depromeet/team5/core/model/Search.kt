package com.depromeet.team5.core.model

import com.depromeet.team5.core.domain.model.SearchEntity
import com.depromeet.team5.core.domain.model.SearchInfoEntity
import com.depromeet.team5.core.model.mapper.ModelMapper


data class Search(
    val code: String,
    val message: String,
    val data: List<SearchInfo>
) : ModelMapper<SearchEntity> {

    override fun toDomain(): SearchEntity = SearchEntity(
        code = code,
        message = message,
        data = data.map { it.toDomain() }
    )
}

data class SearchInfo(
    val market: String,
    val symbol: String,
    val title: String
) : ModelMapper<SearchInfoEntity> {

    override fun toDomain(): SearchInfoEntity = SearchInfoEntity(
        market = market,
        symbol = symbol,
        title = title
    )
}
