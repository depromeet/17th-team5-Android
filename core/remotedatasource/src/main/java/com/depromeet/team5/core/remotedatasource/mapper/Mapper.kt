package com.depromeet.team5.core.remotedatasource.mapper

import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData
import com.depromeet.team5.core.data.request.PrincipleCheckRequestData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.remotedatasource.request.PrincipleCheckRequestRemoteData

fun CreateRetrospectionRequestData.toRemoteData(
    principleChecks: List<PrincipleCheckRequestRemoteData>,
) = CreateRetrospectionRequestRemoteData(
    symbol = symbol,
    market = market,
    currency = currency,
    orderDate = orderDate,
    orderType = orderType,
    price = price,
    volume = volume,
    returnRate = returnRate,
    principleChecks = principleChecks,
)

fun PrincipleCheckRequestData.toRemoteData(
    imageIds: List<Int>,
) = PrincipleCheckRequestRemoteData(
    principleId = principleId,
    status = status,
    reason = reason,
    imageIds = imageIds,
    links = links,
)