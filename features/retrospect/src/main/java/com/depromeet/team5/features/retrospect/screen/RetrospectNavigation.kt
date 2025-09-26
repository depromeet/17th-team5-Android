package com.depromeet.team5.features.retrospect.screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.model.request.CreateRetrospectionParams
import com.depromeet.team5.core.model.request.OrderTypeParams
import kotlinx.serialization.Serializable

@Serializable
data class Retrospect(
    val params: CreateRetrospectionParams
)

fun NavController.navigateToRetrospect(
    symbol: String,
    market: String,
    orderType: OrderTypeParams,
){
    navigate(
        CreateRetrospectionParams.EMPTY.copy(
            symbol = symbol,
            market = market,
            orderType = orderType
        )
    )
}

fun NavGraphBuilder.retrospectScreen(
    onBackPressed: () -> Unit
){
    composable<Retrospect>{
        RetrospectRoute(
            onBackPressed = onBackPressed
        )
    }
}