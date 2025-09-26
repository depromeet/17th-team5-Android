package com.depromeet.team5.core.model.mapper

import com.depromeet.team5.core.domain.model.AnalysisEntity
import com.depromeet.team5.core.domain.model.FeedbackEntity
import com.depromeet.team5.core.domain.model.PrincipleEntity
import com.depromeet.team5.core.domain.model.RetrospectionEntity
import com.depromeet.team5.core.model.Analysis
import com.depromeet.team5.core.model.Feedback
import com.depromeet.team5.core.model.FeedbackPrinciple
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

fun FeedbackEntity.toUi() = Feedback(
    code = code,
    message = message,
    summarize = summarize,
    summarizeOfMarket = summarizeOfMarket,
    principles = principles.map { it.toUi() }
)

fun PrincipleEntity.toUi(): FeedbackPrinciple = FeedbackPrinciple(
    title = title,
    content = content
)

fun AnalysisEntity.toPresentation(): Analysis = Analysis(
    code = code,
    text = text,
    message = message
)