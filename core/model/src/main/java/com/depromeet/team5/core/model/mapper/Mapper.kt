package com.depromeet.team5.core.model.mapper

import com.depromeet.team5.core.domain.model.RetrospectionEntity
import com.depromeet.team5.core.model.Retrospection


fun RetrospectionEntity.toPresentation(): Retrospection {
    return Retrospection(
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
