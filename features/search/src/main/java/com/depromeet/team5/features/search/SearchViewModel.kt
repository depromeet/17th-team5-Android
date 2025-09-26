package com.depromeet.team5.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.usecase.SearchUseCase
import com.depromeet.team5.features.search.model.StockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    val searchUiState: StateFlow<UiState<List<StockData>>> =
        _searchText
            .debounce(300)
            .map { it.trim() }
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    flowOf(UiState.Recents(items))
                } else {
                    searchUseCase.invoke(query)
                        .map { entity ->
                            val list = entity.data.map { info ->
                                StockData(
                                    symbol = info.symbol,
                                    stockName = info.title,
                                    market = info.market
                                )
                            }
                            if (list.isEmpty()) UiState.Empty else UiState.Results(list)
                        }
                        .onStart { emit(UiState.Loading) }
                        .catch { e -> emit(UiState.Error) }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Recents(items)
            )

    companion object StockDummy {
        val items = listOf(
            StockData(symbol = "005930", stockName = "삼성전자", market = "KOSPI"),
            StockData(symbol = "000660", stockName = "SK하이닉스", market = "KOSPI"),
            StockData(symbol = "035420", stockName = "NAVER", market = "KOSPI"),
            StockData(symbol = "035720", stockName = "KAKAO", market = "KOSPI"),
            StockData(symbol = "051910", stockName = "LG화학", market = "KOSPI"),
        )
    }
}
