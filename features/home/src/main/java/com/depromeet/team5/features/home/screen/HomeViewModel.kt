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
import com.depromeet.team5.core.ui.util.flexLocalDateOrNull
import com.depromeet.team5.core.ui.util.toMonthDayOrRaw
import com.depromeet.team5.core.ui.util.toSectionLabel
import com.depromeet.team5.core.ui.util.toYMDOrRaw
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
    val companyName: String,
    val sections: List<RetrospectionSectionState>,
)

data class RetrospectionSectionState(
    val title: String,
    val items: List<RetrospectionState>,
)

data class RetrospectionState(
    val id: Int,
    val dayText: String,
    val price: Int,
    val volume: Int,
    val tradeLabelRes: Int,
    val tradeColor: Color,
    val orderDateText: String,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    userStatsUseCase: UserStatsUseCase,
    retrospectionListUseCase: RetrospectionListUseCase,
    getPrincipleGroupsUseCase: GetPrincipleGroupsUseCase,
    systemPrincipleUseCase: SystemPrincipleUseCase,
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
                initialValue = HedgeUiState.Loading(UserStatsInfo.EMPTY)
            )

    val retrospectionListUiState: StateFlow<HedgeUiState<List<RetrospectionSymbolState>>> =
        retrospectionListUseCase()
            .map { result ->
                if (result.data.isEmpty()) {
                    emptyList()
                } else {
                    result.data.map { companyName ->
                        val items = companyName.retrospections
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
                                        val isBuy = r.orderType == OrderType.BUY.name
                                        RetrospectionState(
                                            id = r.id,
                                            dayText = r.retrospectionCreatedAt.toMonthDayOrRaw(),
                                            price = r.price,
                                            volume = r.volume,
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
                            companyName = companyName.companyName,
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
            .flatMapLatest { order -> getPrincipleGroupsUseCase(order.name) }
            .asUiState()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HedgeUiState.Loading(emptyList())
            )

    fun setPrincipleOrderType(type: OrderType) {
        if (_principleOrderType.value != type) _principleOrderType.value = type
    }
}
