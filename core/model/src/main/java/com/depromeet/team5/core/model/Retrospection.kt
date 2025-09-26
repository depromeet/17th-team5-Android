package com.depromeet.team5.core.model

import androidx.compose.runtime.Stable
import com.depromeet.team5.core.domain.model.RetrospectionEntity
import com.depromeet.team5.core.model.mapper.ModelMapper


@Stable
data class Retrospection(
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
) : ModelMapper<RetrospectionEntity> {

    override fun toDomain(): RetrospectionEntity {
        return RetrospectionEntity(
            id = id,
            userId = userId,
            market = market,
            price = price,
            content = content,
            createdAt = createdAt,
            currency = currency,
            emotion = emotion,
            orderDate = orderDate,
            orderType = orderType,
            returnRate = returnRate,
            symbol = symbol,
            updatedAt = updatedAt,
            volume = volume
        )
    }
}
