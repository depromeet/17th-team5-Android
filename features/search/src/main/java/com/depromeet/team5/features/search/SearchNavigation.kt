package com.depromeet.team5.features.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.depromeet.team5.core.model.request.CreateRetrospectionParams
import com.depromeet.team5.core.model.request.OrderTypeParams
import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val params: CreateRetrospectionParams
)

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit
) {
    composable<Search> {
        SearchRoute(
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToSearch(
    orderType: OrderTypeParams,
) {
    navigate(
        Search(
            CreateRetrospectionParams.EMPTY.copy(
                orderType = orderType
            )
        )
    )
}