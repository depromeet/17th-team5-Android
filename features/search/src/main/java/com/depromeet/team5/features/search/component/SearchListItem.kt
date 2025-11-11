package com.depromeet.team5.features.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.ui.component.HedgeCompanyLogo
import com.depromeet.team5.features.search.model.StockData

@Composable
fun SearchListItem(
    stockData: StockData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HedgeCompanyLogo(
            modifier = Modifier
                .padding(start = 20.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .clip(shape = CircleShape)
                .size(32.dp)
        )

        Text(
            text = stockData.stockName,
            style = HedgeTypography.Body1.SemiBold,
            color = HedgeColor.GREY_900
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchListItemPreview() {
    SearchListItem(
        onClick = {},
        stockData = StockData(
            symbol = "005930",
            market = "KOSPI",
            stockName = "삼성전자",
            stockImageUrl = null
        )
    )
}