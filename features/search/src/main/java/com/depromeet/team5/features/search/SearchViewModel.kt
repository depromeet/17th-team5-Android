package com.depromeet.team5.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.usecase.GetStockSliceUseCase
import com.depromeet.team5.core.domain.usecase.RetrospectionListUseCase
import com.depromeet.team5.features.search.model.StockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getStockSliceUseCase: GetStockSliceUseCase,
    private val retrospectionListUseCase: RetrospectionListUseCase
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    private val recentsFlow: StateFlow<List<StockData>> =
        retrospectionListUseCase()
            .map { list ->
                list.data
                    .map { it.companyName }
                    .distinct()
                    .sorted()
                    .map { company ->
                        StockData(
                            symbol = "",
                            stockName = company,
                            market = "",
                            stockImageUrl = null
                        )
                    }
            }
            .catch { emit(emptyList()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )

    val searchUiState: StateFlow<UiState<List<StockData>>> =
        _searchText
            .debounce(300)
            .map { it.trim() }
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    flowOf(UiState.Recents(recentsFlow.value))
                } else {
                    getStockSliceUseCase(
                        companyName = query,
                        nextCursor = null,
                        size = 10
                    )
                        .map { slice ->
                            val list = slice.data.content.map { info ->
                                StockData(
                                    symbol = info.symbol,
                                    stockName = info.companyName,
                                    market = info.market,
                                    stockImageUrl = info.logo
                                )
                            }
                            if (list.isEmpty()) UiState.Empty else UiState.Results(list)
                        }
                        .onStart { emit(UiState.Loading) }
                        .catch { emit(UiState.Error) }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiState.Recents(recentsFlow.value)
            )
}
