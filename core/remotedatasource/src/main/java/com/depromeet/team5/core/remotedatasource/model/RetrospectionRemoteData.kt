package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.DataData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper


data class RetrospectionRemoteData(
    val code: String,
    val message: String,
    val data: DataRemoteData?
) : RemoteDataMapper<RetrospectionData> {

    override fun toData(): RetrospectionData = RetrospectionData(
        code = code,
        message = message,
        data = data?.toData()
    )
}

data class DataRemoteData(
    val content: String,
    val createdAt: String,
    val currency: String,
    val emotion: String,
    val id: Int,
    val market: String,
    val orderDate: String,
    val orderType: String,
    val price: Int,
    val returnRate: Double,
    val symbol: String,
    val updatedAt: String,
    val userId: Int,
    val volume: Int
) : RemoteDataMapper<DataData> {

    override fun toData(): DataData = DataData(
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
