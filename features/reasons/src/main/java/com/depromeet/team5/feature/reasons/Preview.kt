package com.depromeet.team5.feature.reasons

import androidx.compose.ui.text.input.TextFieldValue
import com.depromeet.team5.core.domain.model.Article
import com.depromeet.team5.core.domain.model.Memo
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.PrincipleType
import com.depromeet.team5.core.ui.model.HedgeBadge
import com.depromeet.team5.feature.reasons.model.PrincipleAdherence
import com.depromeet.team5.feature.reasons.model.TradeInfo
import com.depromeet.team5.feature.reasons.model.UiPrinciple
import com.depromeet.team5.feature.reasons.model.UiPrincipleChecks
import com.depromeet.team5.feature.reasons.model.UiPrincipleGroup
import com.depromeet.team5.feature.reasons.model.UiRetrospection

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

val previewRetrospection = UiRetrospection(
    id = 2903,
    userId = 1,
    symbol = "TSLA",
    market = "NAS",
    companyName = "테슬라",
    companyLogo = "http://foodiy.iptime.org:19000/depromeet/stock/logo/TSLA/TSLA.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20251111T124212Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=RIw5NJg34jD2RouR5xN7%2F20251111%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=e4360f9c74b6357511af8442d2ff02005d7f6f5b462167a5b78ca9b85f007e74",
    orderType = OrderType.SELL,
    price = 10000,
    currency = "USD",
    volume = 10,
    orderDate = "2025-11-10",
    returnRate = 78.83,
    badge = HedgeBadge.SILVER,
    principleGroupState = UiPrincipleGroup(
        id = 2,
        groupName = "초보자를 위한 매도 원칙",
        thumbnail = "🤑",
        principleType = PrincipleType.BUY,
        principles = listOf(
            UiPrinciple(
                id = 1,
                groupId = 2,
                principle = "과열 구간에선 일부 차익을 실현하라",
                description = "",
                principleChecks = UiPrincipleChecks(
                    principleId = 83,
                    adherence = PrincipleAdherence.KEPT,
                    note = TextFieldValue("현재 과열 구간이라 이익 실현했습니다.djfajiodjfioa\nadna\n\n\n\n\n\n\n\nnsdna\na\nd\nan\nadsasd\nadsfadfda\nadfadfda\naadfadfdaf\nadfadf\nadfdafdsafda\n\n\n\n\n\nadsfdasf\n\nasdfadf\n\n\ndfdafdn\n\n\ndfdafdafn\\n\n\ndfajiodjfoajdif"),
                    imageUrls = listOf(
                        "file:///android_asset/placeholder.png",
                        "file:///android_asset/placeholder.png",
                        "file:///android_asset/placeholder.png",
                    ),
                    articles = listOf(
                        Article("https://namu.wiki/w/%EC%9D%BC%EB%A1%A0%20%EB%A8%B8%EC%8A%A4%ED%81%AC", null, null, null),
                        Article("https://namu.wiki/w/%EC%9D%BC%EB%A1%A0%20%EB%A8%B8%EC%8A%A4%ED%81%AC", null, null, null),
                        Article("https://namu.wiki/w/%EC%9D%BC%EB%A1%A0%20%EB%A8%B8%EC%8A%A4%ED%81%AC", null, null, null),
                    ),
                ),
            ),
            UiPrinciple(
                id = 1,
                groupId = 2,
                principle = "감정으로 팔지 않기",
                description = "",
                principleChecks = UiPrincipleChecks(
                    principleId = 83,
                    adherence = PrincipleAdherence.NEUTRAL,
                    note = TextFieldValue("기업 가치는 그냥 머스크라서 산거임."),
                    imageUrls = emptyList(),
                    articles = emptyList()
                ),
            ),
            UiPrinciple(
                id = 1,
                groupId = 2,
                principle = "세금과 수수료 고려하기",
                description = "",
                principleChecks = UiPrincipleChecks(
                    principleId = 83,
                    adherence = PrincipleAdherence.NOT_KEPT,
                    note = TextFieldValue("요즘 폭락 중이라 그냥 팔았어요 ㅠㅠ"),
                    imageUrls = listOf(
                        "file:///android_asset/placeholder.png",
                        "file:///android_asset/placeholder.png",
                        "file:///android_asset/placeholder.png",
                    ),
                    articles = listOf(Article("https://namu.wiki/w/%EC%9D%BC%EB%A1%A0%20%EB%A8%B8%EC%8A%A4%ED%81%AC", null, null, null)),
                ),
            ),
        ),
    ),
    memos = listOf(
        Memo(
            memoId = 1,
            content = "메모111",
            createdAt = "2025-11-11T21:38:04.517452"
        ),
        Memo(
            memoId = 2,
            content = "메모222",
            createdAt = "2025-11-10T21:38:04.517452"
        )
    ),
    createdAt = "2025-11-10T12:15:40.444495",
    updatedAt = "2025-11-10T12:15:40.444508",
)
