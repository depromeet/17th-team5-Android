package com.depromeet.team5.feature.reasons

import androidx.compose.ui.text.input.TextFieldValue
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.PrincipleType
import com.depromeet.team5.feature.reasons.model.PrincipleAdherence
import com.depromeet.team5.feature.reasons.model.TradeInfo
import com.depromeet.team5.feature.reasons.model.UiPrinciple
import com.depromeet.team5.feature.reasons.model.UiPrincipleChecks
import com.depromeet.team5.feature.reasons.model.UiPrincipleGroup

val previewUiPrincipleGroup = UiPrincipleGroup(
    id = 0,
    groupName = "이건 진짜 지켜야 해",
    thumbnail = "👍",
    principleType = PrincipleType.BUY,
    principles = listOf(
        UiPrinciple(
            groupId = 0,
            id = 0,
            principle = "종목 선택 시 최근 매출액 확인하기",
            description = "상승장에서 눌림목 나오면 지지선 나올 때까지 기다렸다가 분할 매수하자. 몰빵은 절대 금지",
            principleChecks = UiPrincipleChecks(
                principleId = 0,
                adherence = PrincipleAdherence.KEPT,
                note = TextFieldValue(""),
                imageUrls = emptyList(),
                articles = emptyList(),
            )
        ),
        UiPrinciple(
            groupId = 0,
            id = 1,
            principle = "커뮤니티 반응 보고 투자 금지",
            description = "description",
            principleChecks = UiPrincipleChecks(
                principleId = 1,
                adherence = PrincipleAdherence.UNSELECTED,
                note = TextFieldValue(""),
                imageUrls = emptyList(),
                articles = emptyList(),
            )
        ),
        UiPrinciple(
            groupId = 0,
            id = 2,
            principle = "감정 로그 활용하기",
            description = "감정 로그를 활용해 ‘불안 시점 vs 실제 하락률’을 비교하면 정확도가 높아진다고 한다. 감정 로그를 꼭 확인하자!",
            principleChecks = UiPrincipleChecks(
                principleId = 2,
                adherence = PrincipleAdherence.UNSELECTED,
                note = TextFieldValue(""),
                imageUrls = emptyList(),
                articles = emptyList(),
            )
        ),
        UiPrinciple(
            groupId = 0,
            id = 3,
            principle = "본질 가치보다 낮게 거래되는 주식 찾아 장기 보유하기",
            description = "기업의 본질 가치보다 낮게 거래되는 주식을 찾아 장기 보유하기",
            principleChecks = UiPrincipleChecks(
                principleId = 3,
                adherence = PrincipleAdherence.UNSELECTED,
                note = TextFieldValue(""),
                imageUrls = emptyList(),
                articles = emptyList(),
            )
        )
    ),
)

val previewTradeInfo = TradeInfo(
    stockName = "Apple",
    orderType = OrderType.BUY,
    price = 65000,
    currency = "$",
    volume = 3,
    orderDate = "2023년 8월 25일",
)