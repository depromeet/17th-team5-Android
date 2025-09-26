package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.RetrospectionEntity


data class RetrospectionData(
    val code: String,
    val message: String,
    val data: DataData?
) : DataMapper<RetrospectionEntity> {

    override fun toDomain(): RetrospectionEntity = RetrospectionEntity(
        id = data?.id ?: -1,
        userId = data?.userId ?: -1,
        market = data?.market ?: "",
        price = data?.price ?: 0,
        content = data?.content ?: "",
        createdAt = data?.createdAt ?: "",
        currency = data?.currency ?: "",
        emotion = data?.emotion ?: "",
        orderDate = data?.orderDate ?: "",
        orderType = data?.orderType ?: "",
        returnRate = data?.returnRate ?: 0.0,
        symbol = data?.symbol ?: "",
        updatedAt = data?.updatedAt ?: "",
        volume = data?.volume ?: 0
    )
}

data class DataData(
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
)
