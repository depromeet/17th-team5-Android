package com.depromeet.team5.features.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.depromeet.team5.core.domain.usecase.GetStockSliceUseCase
import com.depromeet.team5.features.search.model.StockData
import kotlinx.coroutines.flow.first

class StockSlicePagingSource(
    private val getStockSliceUseCase: GetStockSliceUseCase,
    private val query: String,
    private val pageSize: Int = 10
) : PagingSource<String, StockData>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, StockData> {
        return try {
            val cursor = params.key
            val slice = getStockSliceUseCase(
                companyName = query,
                nextCursor = cursor,
                size = pageSize
            ).first()

            val items = slice.data.content.map { info ->
                StockData(
                    symbol = info.symbol,
                    stockName = info.companyName,
                    market = info.market,
                    stockImageUrl = info.logo
                )
            }

            LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = slice.data.nextCursor
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, StockData>): String? {
        return null
    }
}