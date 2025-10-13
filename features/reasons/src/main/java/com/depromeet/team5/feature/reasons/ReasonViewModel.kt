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
import com.depromeet.team5.core.navigation.request.EmotionParams
import com.depromeet.team5.core.navigation.request.PrincipleCheckParams
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

@Stable
enum class Emotion(
    @DrawableRes val iconRes: Int,
    @DrawableRes val enabledIconRes: Int,
    @DrawableRes val disabledIconRes: Int,
    @StringRes val labelRes: Int
) {
    Anxious(
        R.drawable.ic_emotion_anxious,
        R.drawable.ic_emotion_anxious_on,
        R.drawable.ic_emotion_anxious_off,
        R.string.emotion_anxious,
    ),
    Impulse(
        R.drawable.ic_emotion_impulse,
        R.drawable.ic_emotion_impulse_on,
        R.drawable.ic_emotion_impulse_off,
        R.string.emotion_impulse,
    ),
    Neutral(
        R.drawable.ic_emotion_neutral,
        R.drawable.ic_emotion_neutral_on,
        R.drawable.ic_emotion_neutral_off,
        R.string.emotion_neutral,
    ),
    Confidence(
        R.drawable.ic_emotion_confidence,
        R.drawable.ic_emotion_confidence_on,
        R.drawable.ic_emotion_confidence_off,
        R.string.emotion_confidence,
    ),
    Conviction(
        R.drawable.ic_emotion_conviction,
        R.drawable.ic_emotion_conviction_on,
        R.drawable.ic_emotion_conviction_off,
        R.string.emotion_conviction,
    );

    fun toEmotionParams(): EmotionParams {
        return when (this) {
            Anxious -> EmotionParams.ANXIETY
            Impulse -> EmotionParams.IMPULSE
            Neutral -> EmotionParams.MINDLESSNESS
            Confidence -> EmotionParams.CONFIDENCE
            Conviction -> EmotionParams.CONVICTION
        }
    }
}

enum class OrderType {
    BUY, SELL
}

data class TradeInfo(
    @DrawableRes
    val logoDrawableRes: Int,
    val stockName: String,
    val orderType: OrderType,
    val price: Long,
    val currency: String,
    val volume: Int,
    val orderDate: String,
)

data class Principle(
    val id: Long,
    val description: String,
    val checked: Boolean = false,
    val icon: ImageVector? = null,
) {
    fun toPrincipleCheckParams(): PrincipleCheckParams = PrincipleCheckParams(
        isFollowed = checked,
        principleId = id.toInt()
    )
}

fun PrincipleCheckParams.toPrinciple(): Principle {
    return Principle(
        id = principleId.toLong(),
        description = "",
        checked = isFollowed
    )
}

val String.toUiCurrency: String
    get() = if ("KRW" in this) "원" else "$"

internal data class AnalysisRequest(
    val market: String,
    val symbol: String,
    val time: String,
)

private fun formatDate(date: String): String {
    val regex = """(\d{4})년\s*(\d{1,2})월\s*(\d{1,2})일""".toRegex()

    val matchResult = regex.find(date)

    if (matchResult != null) {
        val (year, month, day) = matchResult.destructured

        val formattedMonth = month.padStart(2, '0')
        val formattedDay = day.padStart(2, '0')

        return "$year-$formattedMonth-$formattedDay"
    }

    error("잘못된 Date Format이 들어왔습니다. Params : { $date }")
}


fun String.toIsoUtcString(hour: Int = 9): String {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val localDateTime = LocalDateTime.of(localDate, LocalTime.of(hour, 0, 0))
    return localDateTime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}


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