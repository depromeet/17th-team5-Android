package com.depromeet.team5.feature.reasons

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.usecase.CreateAnalysisUseCase
import com.depromeet.team5.core.navigation.request.OrderTypeParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class TradeInfo(
    @DrawableRes
    val logoDrawableRes: Int,
    val stockName: String,
    val orderType: OrderTypeParams,
    val price: Long,
    val currency: String,
    val volume: Int,
    val orderDate: String,
)

/*
* 업데이트시켜야 할 params
* principle
*   adherence
*   note (비고)
*   List<uri>
*   List<article>
* */

data class Principle(
    val id: Int,
    val title: String,
    val description: String,
    val adherence: PrincipleAdherence,
    val note: TextFieldValue,
    val images: List<String>,
    val articles: List<Article>,
)

enum class PrincipleAdherence {
    UNSELECTED, KEEP, NEUTRAL, BREAK,
}

enum class RestrictionAttachment {
    IMAGE, LINK,
}

data class PrincipleTemplate(
    val id: Int,
    val name: String,
    val emoji: String,
    val principles: List<Principle>
) {

    fun getIndexOfFirstUnselectedPrinciple(): Int {
        return principles.indexOfFirst { it.adherence == PrincipleAdherence.UNSELECTED }
    }

    fun getCheckedPrincipleCount(): Int {
        return principles.count { it.adherence != PrincipleAdherence.UNSELECTED }
    }

    fun isAllPrincipleChecked(): Boolean {
        return getCheckedPrincipleCount() == principles.size
    }

    companion object {
        val RETROSPECT_ENTRY = PrincipleTemplate(
            id = 0,
            name = "이건 진짜 지켜야 해",
            emoji = "👍",
            principles = listOf(
                Principle(
                    id = 0,
                    title = "종목 선택 시 최근 매출액 확인하기",
                    description = "상승장에서 눌림목 나오면 지지선 나올 때까지 기다렸다가 분할 매수하자. 몰빵은 절대 금지",
                    adherence = PrincipleAdherence.KEEP,
                    note = TextFieldValue(""),
                    images = emptyList(),
                    articles = emptyList(),
                ),
                Principle(
                    id = 1,
                    title = "커뮤니티 반응 보고 투자 금지",
                    description = "description",
                    adherence = PrincipleAdherence.UNSELECTED,
                    note = TextFieldValue(""),
                    images = emptyList(),
                    articles = emptyList(),
                ),
                Principle(
                    id = 2,
                    title = "감정 로그 활용하기",
                    description = "감정 로그를 활용해 ‘불안 시점 vs 실제 하락률’을 비교하면 정확도가 높아진다고 한다. 감정 로그를 꼭 확인하자!",
                    adherence = PrincipleAdherence.UNSELECTED,
                    note = TextFieldValue(""),
                    images = emptyList(),
                    articles = emptyList(),
                ),
                Principle(
                    id = 3,
                    title = "본질 가치보다 낮게 거래되는 주식 찾아 장기 보유하기",
                    description = "기업의 본질 가치보다 낮게 거래되는 주식을 찾아 장기 보유하기",
                    adherence = PrincipleAdherence.UNSELECTED,
                    note = TextFieldValue(""),
                    images = emptyList(),
                    articles = emptyList(),
                )
            ),
        )
    }
}

data class Article(
    val originUrl: String,
    val title: String?,
    val thumbnail: String?,
    val source: String?
)

@HiltViewModel
class ReasonsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    createAnalysisUseCase: CreateAnalysisUseCase,
) : ViewModel() {

    private val _selectedEmotion: MutableStateFlow<Emotion?> = MutableStateFlow(null)
    val selectedEmotion = _selectedEmotion.asStateFlow()

    private val _principles = MutableStateFlow(dummyPrinciples)
    val principles = _principles.asStateFlow()

    private val _tradeInfo = MutableStateFlow(dummyTradeInfo)
    val tradeInfo = _tradeInfo.asStateFlow()

    private val createAnalysisRequest = MutableStateFlow<AnalysisRequest?>(null)

    private val _analysisReport: MutableStateFlow<String?> = MutableStateFlow(null)
    val analysisReport = _analysisReport.asStateFlow()

    private val _reason = MutableStateFlow(TextFieldValue(""))
    val reason = _reason.asStateFlow()

    init {
        createAnalysisRequest
            .filterNotNull()
            .onEach {
                runCatching {
                    _analysisReport.value = createAnalysisUseCase(
                        market = it.market,
                        symbol = it.symbol,
                        time = formatDate(it.time).toIsoUtcString(),
                    ).first().text
                }.onFailure {
                    Log.e("ReasonViewModel", "error : $it")
                }
            }
            .launchIn(viewModelScope)
    }

    fun onEmotionChanged(emotion: Emotion) {
        _selectedEmotion.value = emotion
    }

    fun onPrincipleCheckedChanged(principles: List<Principle>) {
        _principles.value = principles
    }

    fun onReasonChanged(reason: TextFieldValue) {
        _reason.value = reason
    }

    fun initTradeInfo(tradeInfo: TradeInfo) {
        _tradeInfo.value = tradeInfo
    }

    internal fun initCreateAnalysisRequest(request: AnalysisRequest) {
        createAnalysisRequest.value = request
    }

    companion object {

        val dummyPrinciples = listOf(
            Principle(1, "안전마진을 확보하라"),
            Principle(2, "분산하되 너무 넓지 않게"),
            Principle(3, "정책 민가몯가 높은 주식은\n 정책 잘 살펴보고 매매"),
            Principle(4, "정보 완전성 기준 세우기"),
            Principle(5, "기업의 본질 가치보다 낮게 거래되는\n주식을 찾아 장기 보유하기"),
            Principle(6, "유행주를 추격하지 않는다."),
            Principle(7, "주가가 오르는 흐름이면 매수,\n하락흐름이면 매도하기"),
            Principle(8, "단기 등락에 흔들리지 말고 기업의 장기\n성장성에 집중하기"),
        )

        val dummyTradeInfo = TradeInfo(
            logoDrawableRes = R.drawable.ic_company_logo,
            stockName = "Apple",
            orderType = OrderType.BUY,
            price = 65000,
            currency = "$",
            volume = 3,
            orderDate = "2023년 8월 25일",
        )
    }
}