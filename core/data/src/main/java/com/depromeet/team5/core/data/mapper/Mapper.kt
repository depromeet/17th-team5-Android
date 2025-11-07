package com.depromeet.team5.core.data.mapper

import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData
import com.depromeet.team5.core.data.request.PrincipleCheckRequestData
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import com.depromeet.team5.core.domain.request.PrincipleCheckRequest

fun CreateRetrospectionRequest.toData() = CreateRetrospectionRequestData(
    symbol = symbol,
    market = market,
    currency = currency,
    orderDate = orderDate,
    orderType = orderType.name,
    price = price,
    volume = volume,
    returnRate = returnRate,
    principleChecks = principleChecks.map { it.toData() }
)

fun PrincipleCheckRequest.toData() = PrincipleCheckRequestData(
    principleId = principleId,
    status = status,
    reason = reason,
    imageIds = imageIds,
    links = links,
)