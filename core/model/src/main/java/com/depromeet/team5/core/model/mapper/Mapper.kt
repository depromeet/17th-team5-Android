package com.depromeet.team5.core.model.mapper

import com.depromeet.team5.core.domain.model.FeedbackEntity
import com.depromeet.team5.core.domain.model.RetrospectionEntity
import com.depromeet.team5.core.model.Feedback
import com.depromeet.team5.core.model.Principle
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

fun FeedbackEntity.toUi(): Feedback {
    //todo 여기서 feedback string을 파싱하여 summarize, summarizeOfMarket, principles로 변환

    return Feedback(
        code = code,
        message = message,
        summarize = "",
        summarizeOfMarket = "",
        principles = listOf(
            Principle(
                title = "",
                content = ""
            ),
            Principle(
                title = "",
                content = ""
            ),
            Principle(
                title = "",
                content = ""
            )
        )
    )
}
