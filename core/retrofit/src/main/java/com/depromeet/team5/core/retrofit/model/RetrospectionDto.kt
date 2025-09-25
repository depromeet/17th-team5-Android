package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.DataResponse
import com.depromeet.team5.core.remotedatasource.model.RetrospectionResponse
import com.depromeet.team5.core.retrofit.mapper.ResponseMapper


data class RetrospectionDto(
    val code: String,
    val data: DataDto?,
    val message: String
) : ResponseMapper<RetrospectionResponse> {

    override fun toRemoteResponse() = RetrospectionResponse(
        code = code,
        data = data?.toRemoteResponse(),
        message = message
    )
}

data class DataDto(
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
) : ResponseMapper<DataResponse> {

    override fun toRemoteResponse(): DataResponse = DataResponse(
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
