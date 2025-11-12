package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.RetrospectionList
import com.depromeet.team5.core.domain.model.RetrospectionListItem
import com.depromeet.team5.core.domain.model.RetrospectionListSymbol

data class RetrospectionListData(
    val code: String,
    val message: String,
    val data: List<RetrospectionListSymbolData>
) : DataMapper<RetrospectionList> {
    override fun toDomain(): RetrospectionList = RetrospectionList(
        code = code,
        message = message,
        data = data.map { it.toDomain() }
    )
}

data class RetrospectionListSymbolData(
    val companyName: String,
    val image: String?,
    val symbol: String,
    val market: String,
    val retrospections: List<RetrospectionListItemData>
) : DataMapper<RetrospectionListSymbol> {
    override fun toDomain(): RetrospectionListSymbol = RetrospectionListSymbol(
        companyName = companyName,
        image = image,
        symbol = symbol,
        market = market,
        retrospections = retrospections.map { it.toDomain() }
    )
}

data class RetrospectionListItemData(
    val id: Int,
    val orderType: String,
    val price: Int,
    val volume: Int,
    val retrospectionCreatedAt: String,
    val orderCreatedAt: String
) : DataMapper<RetrospectionListItem> {
    override fun toDomain(): RetrospectionListItem = RetrospectionListItem(
        id = id,
        orderType = orderType,
        price = price,
        volume = volume,
        retrospectionCreatedAt = retrospectionCreatedAt,
        orderCreatedAt = orderCreatedAt
    )
}