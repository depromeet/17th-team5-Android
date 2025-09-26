package com.depromeet.team5.features.search

import androidx.lifecycle.ViewModel
import com.depromeet.team5.features.search.model.StockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {
//    private val _searchUiState =
//        MutableStateFlow<UiState<List<StockData>>>(UiState.Recents(emptyList()))

    private val _searchUiState =
        MutableStateFlow<UiState<List<StockData>>>(UiState.Recents(items))
    val searchUiState = _searchUiState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

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