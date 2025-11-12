package com.depromeet.team5.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
import kotlinx.coroutines.flow.distinctUntilChanged
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

    val recents: StateFlow<List<StockData>> =
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

    val pagingDataFlow: StateFlow<PagingData<StockData>> =
        _searchText
            .debounce(300)
            .map { it.trim() }
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    flowOf(PagingData.empty())
                } else {
                    Pager(
                        config = PagingConfig(
                            pageSize = 10,
                            prefetchDistance = 3,
                            enablePlaceholders = false
                        ),
                        pagingSourceFactory = {
                            StockSlicePagingSource(
                                getStockSliceUseCase = getStockSliceUseCase,
                                query = query
                            )
                        }
                    ).flow
                }
            }
            .cachedIn(viewModelScope)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PagingData.empty()
            )
}
