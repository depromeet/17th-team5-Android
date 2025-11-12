package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.RetrospectionListData
import com.depromeet.team5.core.data.model.RetrospectionListItemData
import com.depromeet.team5.core.data.model.RetrospectionListSymbolData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

data class RetrospectionListRemoteData(
    val code: String,
    val message: String,
    val data: List<RetrospectionListSymbolRemoteData>
) : RemoteDataMapper<RetrospectionListData> {
    override fun toData(): RetrospectionListData = RetrospectionListData(
        code = code,
        message = message,
        data = data.map { it.toData() }
    )
}

data class RetrospectionListSymbolRemoteData(
    val companyName: String,
    val image: String?,
    val symbol: String,
    val market: String,
    val retrospections: List<RetrospectionListItemRemoteData>
) : RemoteDataMapper<RetrospectionListSymbolData> {
    override fun toData(): RetrospectionListSymbolData = RetrospectionListSymbolData(
        companyName = companyName,
        image = image,
        symbol = symbol,
        market = market,
        retrospections = retrospections.map { it.toData() }
    )
}

data class RetrospectionListItemRemoteData(
    val id: Int,
    val orderType: String,
    val price: Int,
    val volume: Int,
    val retrospectionCreatedAt: String,
    val orderCreatedAt: String
) : RemoteDataMapper<RetrospectionListItemData> {
    override fun toData(): RetrospectionListItemData = RetrospectionListItemData(
        id = id,
        orderType = orderType,
        price = price,
        volume = volume,
        retrospectionCreatedAt = retrospectionCreatedAt,
        orderCreatedAt = orderCreatedAt
    )
}