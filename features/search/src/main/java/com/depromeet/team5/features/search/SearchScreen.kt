package com.depromeet.team5.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.component.HedgeTextField
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.features.search.component.SearchListItem
import com.depromeet.team5.features.search.model.StockData
import androidx.compose.ui.graphics.Color

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
        modifier = modifier
    )
}

@Composable
private fun SearchScreen(
    onBackClick: () -> Unit,
    searchText: String,
    searchUiState: UiState<List<StockData>>,
    onSearchTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HedgeTopBar(onClickBack = onBackClick)

        Text(
            text = stringResource(id = R.string.search_title),
            style = HedgeTypography.Headline1.SemiBold,
            color = HedgeColor.GREY_900,
            modifier = Modifier.padding(top = 16.dp, start = 20.dp, bottom = 10.dp)
        )

        HedgeTextField.Search(
            value = searchText,
            onValueChange = onSearchTextChange,
            placeholder = stringResource(R.string.search_textfield_hint),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 8.dp, bottom = 16.dp)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 194.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = HedgeIcon.Error,
                        contentDescription = "error",
                        tint = Color.Unspecified
                    )

                    Text(
                        text = stringResource(id = R.string.search_error_title),
                        style = HedgeTypography.Body1.Medium,
                        color = HedgeColor.Text.Secondary,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.search_error_description),
                        style = HedgeTypography.Body3.Medium,
                        color = HedgeColor.Text.Assistive,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
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

@Preview(showBackground = true)
@Composable
private fun SearchErrorPreview() {
    SearchScreen(
        onBackClick = {},
        searchText = "",
        searchUiState = UiState.Error("error"),
        onSearchTextChange = {}
    )
}