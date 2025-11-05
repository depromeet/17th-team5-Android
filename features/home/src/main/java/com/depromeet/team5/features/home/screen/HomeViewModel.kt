package com.depromeet.team5.features.home.screen

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.BLUE_500
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.GREY_400
import com.depromeet.team5.core.designsystem.foundation.HedgeColor.RED_500
import com.depromeet.team5.core.domain.model.DefaultPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.RecommendedPrinciple
import com.depromeet.team5.core.domain.model.UserStatsInfo
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.monad.asUiState
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupsUseCase
import com.depromeet.team5.core.domain.usecase.RetrospectionListUseCase
import com.depromeet.team5.core.domain.usecase.SystemPrincipleUseCase
import com.depromeet.team5.core.domain.usecase.UserStatsUseCase
import com.depromeet.team5.features.home.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

enum class HomeTab(
    @StringRes val title: Int,
    val selectedColor: Color = Color.Black,
    val unselectedColor: Color = GREY_400,
) {
    HOME(
        title = R.string.home_tab_title_home
    ),
    PRINCIPLE(
        title = R.string.home_tab_title_principle
    )
}

data class RetrospectionSymbolState(
    val symbol: String,
    val sections: List<RetrospectionSectionState>,
)

data class RetrospectionSectionState(
    val title: String,
    val items: List<RetrospectionState>,
)

data class RetrospectionState(
    val id: Int,
    val dayText: String,
    val priceVolumeText: String,
    val tradeLabelRes: Int,
    val tradeColor: Color,
    val orderDateText: String,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userStatsUseCase: UserStatsUseCase,
    private val retrospectionListUseCase: RetrospectionListUseCase,
    private val getPrincipleGroupsUseCase: GetPrincipleGroupsUseCase,
    private val systemPrincipleUseCase: SystemPrincipleUseCase,
) : ViewModel() {
    private val _principleOrderType = MutableStateFlow<OrderType>(OrderType.BUY)
    val principleOrderType: StateFlow<OrderType> = _principleOrderType.asStateFlow()

    val userStatsUiState: StateFlow<HedgeUiState<UserStatsInfo>> =
        userStatsUseCase()
            .map { it.data }
            .asUiState()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HedgeUiState.Loading(UserStatsInfo(0, 0, 0, 0, 0))
            )

    val retrospectionListUiState: StateFlow<HedgeUiState<List<RetrospectionSymbolState>>> =
        retrospectionListUseCase()
            .map { result ->
                if (result.data.isEmpty()) {
                    emptyList()
                } else {
                    result.data.map { symbol ->
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
                                            tradeLabelRes = if (isBuy)
                                                R.string.home_tab_retrospection_trade_buy
                                            else
                                                R.string.home_tab_retrospection_trade_sell,
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
                }
            }
            .asUiState()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HedgeUiState.Loading(emptyList())
            )

    val recommendedPrinciplesUiState: StateFlow<HedgeUiState<List<RecommendedPrinciple>>> =
        systemPrincipleUseCase()
            .map { it.data.recommended }
            .asUiState()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HedgeUiState.Loading(emptyList())
            )

    val defaultPrinciplesUiState: StateFlow<HedgeUiState<List<DefaultPrinciple>>> =
        systemPrincipleUseCase()
            .map { it.data.defaults }
            .asUiState()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HedgeUiState.Loading(emptyList())
            )

    val principleGroupsUiState: StateFlow<HedgeUiState<List<MyPrincipleGroup>>> =
        _principleOrderType
            .map { it.name }
            .flatMapLatest { order -> getPrincipleGroupsUseCase(order) }
            .asUiState()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HedgeUiState.Loading(emptyList())
            )

    fun setPrincipleOrderType(type: OrderType) {
        if (_principleOrderType.value != type) _principleOrderType.value = type
    }

    private val INPUT_DATE_FORMATTERS = listOf(
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ISO_LOCAL_DATE,
        DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA)
    )

    private val OUT_YMD = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA)
    private val OUT_MD = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREA)
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
