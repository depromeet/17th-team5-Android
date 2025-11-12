package com.depromeet.team5.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.depromeet.team5.core.designsystem.component.HedgeTextField
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.search.component.SearchListItem
import com.depromeet.team5.features.search.model.StockData
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchRoute(
    onBackClick: () -> Unit,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    requestViewModel: RequestViewModel = hiltViewModel(),
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val recents by viewModel.recents.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    SearchScreen(
        onBackClick = onBackClick,
        searchText = searchText,
        recents = recents,
        pagingItems = pagingItems,
        onSearchTextChange = viewModel::updateSearchText,
        onItemClick = {
            requestViewModel.request =
                requestViewModel.request.copy(
                    symbol = it.symbol,
                    market = it.market,
                    companyName = it.stockName
                )
            onItemClick()
        },
        modifier = modifier.background(HedgeColor.Neutral.BackgroundDefault)
    )
}

@Composable
private fun SearchScreen(
    onBackClick: () -> Unit,
    searchText: String,
    recents: List<StockData>,
    pagingItems: LazyPagingItems<StockData>,
    onSearchTextChange: (String) -> Unit,
    onItemClick: (StockData) -> Unit,
    modifier: Modifier = Modifier,
) {
    val showHeader =
        if (searchText.isBlank()) {
            true
        } else {
            pagingItems.loadState.refresh !is LoadState.Error
        }

    SearchLayout(
        modifier = modifier,
        onBackClick = onBackClick,
        showHeader = showHeader,
        searchText = searchText,
        onSearchTextChange = onSearchTextChange,
        content = {
            if (searchText.isBlank()) {
                RecentsContent(recents, onItemClick)
            } else {
                ResultsPagingContent(
                    items = pagingItems,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Composable
private fun SearchLayout(
    onBackClick: () -> Unit,
    showHeader: Boolean,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        HedgeTopBar(onClickBack = onBackClick)

        if (showHeader) {
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
        }

        content()
    }
}

@Composable
private fun RecentsContent(
    items: List<StockData>,
    onItemClick: (StockData) -> Unit,
) {
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
                modifier = Modifier
                    .background(HedgeColor.Neutral.BackgroundDefault)
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
            )
        }
        items(items, key = { it.symbol }) { item ->
            SearchListItem(stockData = item, onClick = { onItemClick(item) })
        }
    }
}

@Composable
private fun ResultsPagingContent(
    items: LazyPagingItems<StockData>,
    onItemClick: (StockData) -> Unit,
) {
    val loadState = items.loadState

    when {
        loadState.refresh is LoadState.Loading -> {
            LoadingContent()
            return
        }

        loadState.refresh is LoadState.Error -> {
            ErrorContent()
            return
        }

        loadState.refresh is LoadState.NotLoading && items.itemCount == 0 -> {
            EmptyContent()
            return
        }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(
            count = items.itemCount,
            key = { index ->
                val item = items[index]
                if (item?.symbol?.isNotBlank() == true) item.symbol
                else item?.stockName ?: index.toString()
            }
        ) { index ->
            val item = items[index]
            if (item != null) {
                SearchListItem(
                    stockData = item,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    // 로딩뷰
}

@Composable
private fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = HedgeIcon.Empty,
            contentDescription = "empty",
            tint = Color.Unspecified
        )

        Text(
            text = stringResource(id = R.string.search_empty_title),
            style = HedgeTypography.Body1.Medium,
            color = HedgeColor.Text.Secondary,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = stringResource(id = R.string.search_empty_description),
            style = HedgeTypography.Body3.Medium,
            color = HedgeColor.Text.Assistive,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
private fun ErrorContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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

@Preview(showBackground = true)
@Composable
private fun RecentsPreview() {
    val recent = listOf(
        StockData(
            symbol = "005930",
            stockName = "삼성전자",
            market = "",
            stockImageUrl = ""
        ),
        StockData(
            symbol = "000660",
            stockName = "SK하이닉스",
            market = "",
            stockImageUrl = ""
        ),
        StockData(
            symbol = "035420",
            stockName = "NAVER",
            market = "",
            stockImageUrl = ""
        ),
    )

    val dummyPagingItems = flowOf(PagingData.empty<StockData>()).collectAsLazyPagingItems()

    SearchScreen(
        onBackClick = {},
        searchText = "",
        recents = recent,
        pagingItems = dummyPagingItems,
        onSearchTextChange = {},
        onItemClick = {},
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
            market = "",
            stockImageUrl = ""
        ),
        StockData(
            symbol = "051910",
            stockName = "LG화학",
            market = "",
            stockImageUrl = ""

        ),
    )

    val dummyPagingItems = flowOf(PagingData.empty<StockData>()).collectAsLazyPagingItems()

    SearchScreen(
        onBackClick = {},
        searchText = "카",
        recents = emptyList(),
        pagingItems = dummyPagingItems,
        onSearchTextChange = {},
        onItemClick = {},
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {
    SearchRoute(
        onBackClick = {},
        onItemClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchEmptyPreview() {
    val pagingItems = flowOf(PagingData.empty<StockData>()).collectAsLazyPagingItems()

    SearchScreen(
        onBackClick = {},
        searchText = "",
        recents = emptyList(),
        pagingItems = pagingItems,
        onSearchTextChange = {},
        onItemClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchErrorPreview() {
    ErrorContent()
}