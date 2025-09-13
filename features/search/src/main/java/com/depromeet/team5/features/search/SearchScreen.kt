package com.depromeet.team5.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.features.search.component.SearchListItem
import com.depromeet.team5.features.search.component.SearchTextField
import com.depromeet.team5.features.search.model.StockData

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()

    SearchScreen(
        searchText = searchText,
        searchUiState = searchUiState,
        onSearchTextChange = viewModel::updateSearchText,
        onDeleteClick = viewModel::deleteSearchText,
        modifier = modifier
    )
}

@Composable
private fun SearchScreen(
    searchText: String,
    searchUiState: UiState<List<StockData>>,
    onSearchTextChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.search_title),
            style = HedgeTypography.Headline1.SemiBold,
            color = HedgeColor.GREY_900,
            modifier = Modifier.padding(top = 16.dp, start = 20.dp, bottom = 10.dp)
        )

        SearchTextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            onSearchClick = {},
            onDeleteClick = onDeleteClick,
            modifier = Modifier.padding(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 16.dp)
        )

        when (searchUiState) {
            is UiState.Default -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    stickyHeader {
                        Text(
                            text = stringResource(id = R.string.search_retrospect_list),
                            fontSize = 15.sp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                        )
                    }

                    items(
                        items = searchUiState.data,
                        key = { it.symbol }
                    ) { item ->
                        SearchListItem(
                            stockData = item,
                            onClick = {}
                        )
                    }
                }

            }

            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        items = searchUiState.data,
                        key = { it.symbol }
                    ) { item ->
                        SearchListItem(
                            stockData = item,
                            onClick = {}
                        )
                    }
                }
            }

            is UiState.Error -> {
                // 에러뷰
            }

            is UiState.Loading -> {
                // 로딩뷰
            }

            is UiState.Empty -> {
                // 검색 결과 없음 뷰
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    SearchRoute(
        viewModel = SearchViewModel()
    )
}