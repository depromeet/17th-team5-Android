package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.DataRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper


data class RetrospectionResponse(
    val code: String,
    val data: DataResponse?,
    val message: String
) : RetrofitMapper<RetrospectionRemoteData> {

    override fun toRemoteData() = RetrospectionRemoteData(
        code = code,
        data = data?.toRemoteData(),
        message = message
    )
}

data class DataResponse(
    val id: Int,
    val userId: Int,
    val market: String,
    val price: Int,
    val content: String,
    val createdAt: String,
    val currency: String,
    val emotion: String,
    val orderDate: String,
    val orderType: String,
    val returnRate: Double,
    val symbol: String,
    val updatedAt: String,
    val volume: Int
) : RetrofitMapper<DataRemoteData> {

    override fun toRemoteData() = DataRemoteData(
        content = content,
        createdAt = createdAt,
        currency = currency,
        emotion = emotion,
        id = id,
        market = market,
        orderDate = orderDate,
        orderType = orderType,
        price = price,
        returnRate = returnRate,
        symbol = symbol,
        updatedAt = updatedAt,
        userId = userId,
        volume = volume
    )
}
