package com.depromeet.team5.feature.reasons.model

import androidx.compose.runtime.Immutable
import com.depromeet.team5.core.domain.model.Article
import com.depromeet.team5.core.domain.model.Memo
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.Retrospection
import com.depromeet.team5.core.ui.model.HedgeBadge

@Immutable
data class UiRetrospection(
    val id: Int,
    val userId: Int,
    val market: String,
    val companyName: String,
    val companyLogo: String,
    val price: Int,
    val createdAt: String,
    val currency: String,
    val orderDate: String,
    val orderType: OrderType,
    val returnRate: Double,
    val badge: HedgeBadge,
    val symbol: String,
    val updatedAt: String,
    val volume: Int,
    val principleGroupState: UiPrincipleGroup,
    val memos: List<Memo>
)

suspend fun Retrospection.toUi(
    parseArticle: (suspend (String) -> Article)? = null
) = UiRetrospection(
    id = id,
    userId = userId,
    market = market,
    companyName = companyName,
    companyLogo = companyLogo,
    price = price,
    createdAt = createdAt,
    currency = currency,
    orderDate = orderDate,
    orderType = orderType,
    returnRate = returnRate,
    badge = HedgeBadge.fromBadge(badge),
    symbol = symbol,
    updatedAt = updatedAt,
    volume = volume,
    principleGroupState = principleGroupState
        ?.toUi(parseArticle)
        ?: error("principleGroupState must not be null for retrospection detail (id=$id)"),
    memos = memos
)