package com.depromeet.team5.core.remotedatasource.mapper

import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData
import com.depromeet.team5.core.data.request.PrincipleCheckRequestData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.remotedatasource.request.PrincipleCheckRequestRemoteData

fun CreateRetrospectionRequestData.toRemoteData() = CreateRetrospectionRequestRemoteData(
    symbol = symbol,
    market = market,
    currency = currency,
    orderDate = orderDate,
    orderType = orderType,
    price = price,
    volume = volume,
    returnRate = returnRate,
    principleChecks = principleChecks.map { it.toRemoteData() },
)

fun PrincipleCheckRequestData.toRemoteData() = PrincipleCheckRequestRemoteData(
    principleId = principleId,
    status = status,
    reason = reason,
    imageIds = imageUrls,
    links = links,
)