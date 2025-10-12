package com.depromeet.team5.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.depromeet.team5.core.navigation.request.OrderTypeParams
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.features.home.component.HomeFloatingActionButton

@Composable
fun HomeRoute(
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    requestViewModel: RequestViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    HomeScreen(
        onBuyClick = {
            requestViewModel.request =
                requestViewModel.request.copy(orderType = OrderTypeParams.BUY)
            onBuyClick()
        },
        onSellClick = {
            requestViewModel.request =
                requestViewModel.request.copy(orderType = OrderTypeParams.SELL)
            onSellClick()
        },
        modifier = modifier
    )
}

@Composable
private fun HomeScreen(
    onBuyClick: () -> Unit,
    onSellClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fabChecked by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        HomeFloatingActionButton(
            fabChecked = fabChecked,
            onCheckedChange = { fabChecked = !fabChecked },
            onBuyClick = onBuyClick,
            onSellClick = onSellClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    HomeScreen(
        onBuyClick = {},
        onSellClick = {}
    )
}