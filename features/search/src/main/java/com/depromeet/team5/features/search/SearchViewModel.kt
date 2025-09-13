package com.depromeet.team5.features.search

import androidx.lifecycle.ViewModel
import com.depromeet.team5.features.search.model.StockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel(){
    private val _searchUiState = MutableStateFlow<UiState<List<StockData>>>(UiState.Recents(emptyList()))
    val searchUiState = _searchUiState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun updateSearchText(text: String){
        _searchText.value = text
    }

    fun deleteSearchText(){
        _searchText.value = ""
    }
}