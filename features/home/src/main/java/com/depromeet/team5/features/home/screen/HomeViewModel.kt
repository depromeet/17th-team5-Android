package com.depromeet.team5.features.home.screen

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.BLUE_500
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.GREY_400
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.RED_500
import com.depromeet.team5.core.domain.usecase.RetrospectionListUseCase
import com.depromeet.team5.core.domain.usecase.UserStatsUseCase
import com.depromeet.team5.features.home.R
import com.depromeet.team5.features.home.RetrospectionListUiState
import com.depromeet.team5.features.home.RetrospectionSectionState
import com.depromeet.team5.features.home.RetrospectionState
import com.depromeet.team5.features.home.RetrospectionSymbolState
import com.depromeet.team5.features.home.UserStatsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

enum class HomeTab(
    @StringRes val title: Int,
    val selectedColor: Color = Color.Black,
    val unselectedColor: Color = GREY_400
) {
    HOME(
        title = R.string.home_tab_title_home
    ),
    PRINCIPLE(
        title = R.string.home_tab_title_principle
    )
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userStatsUseCase: UserStatsUseCase,
    private val retrospectionListUseCase: RetrospectionListUseCase
) : ViewModel() {
    private val _userStatsUiStateFlow: MutableStateFlow<UserStatsUiState> =
        MutableStateFlow(UserStatsUiState.Loading)
    val userStatsUiStateFlow: StateFlow<UserStatsUiState> = _userStatsUiStateFlow

    private val _retrospectionListUiStateFlow: MutableStateFlow<RetrospectionListUiState> =
        MutableStateFlow(RetrospectionListUiState.Loading)
    val retrospectionListUiStateFlow: StateFlow<RetrospectionListUiState> =
        _retrospectionListUiStateFlow

    init {
        userStats()
        getRetrospectionList()
    }

    fun userStats() {
        viewModelScope.launch {
            userStatsUseCase()
                .map {
                    UserStatsUiState.Success(
                        percentage = it.data.percentage,
                        hedge = it.data.hedge,
                        bronze = it.data.bronze,
                        silver = it.data.silver,
                        gold = it.data.gold
                    )
                }
                .onStart { _userStatsUiStateFlow.value = UserStatsUiState.Loading }
                .catch { t -> _userStatsUiStateFlow.value = UserStatsUiState.Failure(t) }
                .collect { success -> _userStatsUiStateFlow.value = success }
        }
    }

    fun getRetrospectionList() {
        viewModelScope.launch {
            retrospectionListUseCase()
                .map { result ->
                    if (result.data.isEmpty()) {
                        RetrospectionListUiState.Empty
                    } else {
                        val uiSymbols = result.data.map { symbol ->
                            val items = symbol.retrospections
                            val sections = items
                                .sortedByDescending {
                                    it.retrospectionCreatedAt.flexLocalDateOrNull() ?: LocalDate.MIN
                                }
                                .groupBy {
                                    it.retrospectionCreatedAt.flexLocalDateOrNull().toSectionLabel()
                                }
                                .map { (title, group) ->
                                    RetrospectionSectionState(
                                        title = title,
                                        items = group.map { r ->
                                            val isBuy = r.orderType == "BUY"
                                            RetrospectionState(
                                                id = r.id,
                                                dayText = r.retrospectionCreatedAt.toMonthDayOrRaw(),
                                                priceVolumeText = "%,d원 • %d주".format(
                                                    r.price,
                                                    r.volume
                                                ),
                                                tradeLabelRes = if (isBuy) R.string.home_tab_retrospection_trade_buy else R.string.home_tab_retrospection_trade_sell,
                                                tradeColor = if (isBuy) RED_500 else BLUE_500,
                                                orderDateText = r.orderCreatedAt.toYMDOrRaw()
                                            )
                                        }
                                    )
                                }
                            RetrospectionSymbolState(
                                symbol = symbol.symbol,
                                sections = sections
                            )
                        }
                        RetrospectionListUiState.Success(symbols = uiSymbols)
                    }
                }
                .onStart { _retrospectionListUiStateFlow.value = RetrospectionListUiState.Loading }
                .catch { t ->
                    _retrospectionListUiStateFlow.value = RetrospectionListUiState.Failure(t)
                }
                .collect { s -> _retrospectionListUiStateFlow.value = s }
        }
    }

    private val INPUT_DATE_FORMATTERS = listOf(
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ISO_LOCAL_DATE,
        DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA)
    )

    private val OUT_YMD = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA)
    private val OUT_MD  = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREA)
    private val OUT_YYM = DateTimeFormatter.ofPattern("yy년 M월", Locale.KOREA)

    private fun String.flexLocalDateOrNull(): LocalDate? {
        for (fmt in INPUT_DATE_FORMATTERS) {
            val parsed = runCatching {
                if (fmt == DateTimeFormatter.ISO_LOCAL_DATE_TIME) {
                    java.time.LocalDateTime.parse(this, fmt).toLocalDate()
                } else {
                    LocalDate.parse(this, fmt)
                }
            }.getOrNull()
            if (parsed != null) return parsed
        }
        return null
    }

    private fun String.toMonthDayOrRaw(): String =
        this.flexLocalDateOrNull()?.format(OUT_MD) ?: this

    private fun String.toYMDOrRaw(): String =
        this.flexLocalDateOrNull()?.format(OUT_YMD) ?: this

    private fun LocalDate?.toSectionLabel(now: LocalDate = LocalDate.now()): String {
        val thisMonth = YearMonth.from(now)
        val target = this?.let { YearMonth.from(it) } ?: return "기타"
        return when (target) {
            thisMonth -> "이번달 회고"
            thisMonth.minusMonths(1) -> "지난달 회고"
            else -> target.format(OUT_YYM) + " 회고"
        }
    }
}
