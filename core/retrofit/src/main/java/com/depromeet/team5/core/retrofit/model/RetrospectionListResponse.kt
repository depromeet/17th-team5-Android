package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.RetrospectionListRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListItemRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionListSymbolRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.Serializable

@Serializable
data class RetrospectionListResponse(
    val code: String,
    val message: String,
    val data: List<RetrospectionListSymbolResponse>
) : RetrofitMapper<RetrospectionListRemoteData> {
    override fun toRemoteData(): RetrospectionListRemoteData = RetrospectionListRemoteData(
        code = code,
        message = message,
        data = data.map { it.toRemoteData() }
    )
}

@Serializable
data class RetrospectionListSymbolResponse(
    val companyName: String,
    val image: String?,
    val symbol: String,
    val market: String,
    val retrospections: List<RetrospectionListItemResponse>
) : RetrofitMapper<RetrospectionListSymbolRemoteData> {
    override fun toRemoteData(): RetrospectionListSymbolRemoteData = RetrospectionListSymbolRemoteData(
        companyName = companyName,
        image = image,
        symbol = symbol,
        market = market,
        retrospections = retrospections.map { it.toRemoteData() }
    )
}

@Serializable
data class RetrospectionListItemResponse(
    val id: Int,
    val orderType: String,
    val price: Int,
    val volume: Int,
    val retrospectionCreatedAt: String,
    val orderCreatedAt: String
) : RetrofitMapper<RetrospectionListItemRemoteData> {
    override fun toRemoteData(): RetrospectionListItemRemoteData = RetrospectionListItemRemoteData(
        id = id,
        orderType = orderType,
        price = price,
        volume = volume,
        retrospectionCreatedAt = retrospectionCreatedAt,
        orderCreatedAt = orderCreatedAt
    )
}