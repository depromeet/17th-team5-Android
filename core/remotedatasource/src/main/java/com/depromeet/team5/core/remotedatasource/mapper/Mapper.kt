package com.depromeet.team5.core.remotedatasource.mapper

import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData
import com.depromeet.team5.core.data.request.PrincipleCheckRequestData
import com.depromeet.team5.core.remotedatasource.request.CreateRetrospectionRequestRemoteData
import com.depromeet.team5.core.remotedatasource.request.PrincipleCheckRequestRemoteData
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun CreateRetrospectionRequestData.toRemoteData(
    upload: suspend (String) -> Int
) = CreateRetrospectionRequestRemoteData(
    symbol = symbol,
    market = market,
    currency = currency,
    orderDate = orderDate,
    orderType = orderType,
    price = price,
    volume = volume,
    returnRate = returnRate,
    principleChecks = principleChecks.map { it.toRemoteData(upload) }
)

suspend fun PrincipleCheckRequestData.toRemoteData(
    upload: suspend (String) -> Int
): PrincipleCheckRequestRemoteData = coroutineScope {
    val ids: List<Int> =
        if (imageUrls.isEmpty()) emptyList()
        else imageUrls.map { url -> async { upload(url) } }.awaitAll()
    PrincipleCheckRequestRemoteData(
        principleId = principleId,
        status = status,
        reason = reason,
        imageIds = ids,
        links = links
    )
}