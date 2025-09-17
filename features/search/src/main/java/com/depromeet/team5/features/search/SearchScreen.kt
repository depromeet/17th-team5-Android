package com.depromeet.team5.features.search

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.features.search.component.SearchListItem
import com.depromeet.team5.features.search.component.SearchTextField
import com.depromeet.team5.features.search.model.StockData

@Composable
fun SearchRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()

    SearchScreen(
        onBackClick = onBackClick,
        searchText = searchText,
        searchUiState = searchUiState,
        onSearchTextChange = viewModel::updateSearchText,
        onDeleteClick = viewModel::deleteSearchText,
        modifier = modifier
    )
}

@Composable
private fun SearchScreen(
    onBackClick: () -> Unit,
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
                modifier = Modifier
                    .clickable { onBackClick() }
                    .size(40.dp),
                tint = HedgeColor.GREY_900
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
            is UiState.Recents -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    stickyHeader {
                        Text(
                            text = stringResource(id = R.string.search_retrospect_list),
                            style = HedgeTypography.Body3.Medium,
                            color = HedgeColor.Text.Alternative,
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

            is UiState.Results -> {
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
private fun RecentsPreview() {
    val recent = listOf(
        StockData(
            symbol = "005930",
            stockName = "삼성전자",
            market = ""
        ),
        StockData(
            symbol = "000660",
            stockName = "SK하이닉스",
            market = ""
        ),
        StockData(
            symbol = "035420",
            stockName = "NAVER",
            market = ""
        ),
    )

    SearchScreen(
        onBackClick = {},
        searchText = "",
        searchUiState = UiState.Recents(recent),
        onSearchTextChange = {},
        onDeleteClick = {},
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun ResultsPreview() {
    val results = listOf(
        StockData(
            symbol = "035720",
            stockName = "카카오",
            market = ""
        ),
        StockData(
            symbol = "051910",
            stockName = "LG화학",
            market = ""
        ),
    )

    SearchScreen(
        onBackClick = {},
        searchText = "카",
        searchUiState = UiState.Results(results),
        onSearchTextChange = {},
        onDeleteClick = {},
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {
    SearchRoute(
        onBackClick = {}
    )
}