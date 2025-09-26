package com.depromeet.team5.features.principle.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.model.request.CreateRetrospectionParams
import com.depromeet.team5.features.principle.PrincipleRoute
import kotlinx.serialization.Serializable


@Serializable
data class Principle(
    val params: CreateRetrospectionParams,
)

fun NavController.navigationPrinciple(
    symbol: String,
    market: String,
    currency: String,
    volume: Int,
    orderDate: String,
    orderType: OrderType,
    price: Int,
    returnRate: Double?,
) {
    navigate(
        route = Principle(
            params = CreateRetrospectionParams.EMPTY.copy(
                symbol = symbol,
                market = market,
                currency = currency,
                volume = volume,
                orderDate = orderDate,
                orderType = orderType,
                price = price,
                returnRate = returnRate,
            )
        )
    )
}

fun NavGraphBuilder.principleScreen() {
    composable<Principle> {
        PrincipleRoute(
            onBackPressed = {}
        )
    }
}
