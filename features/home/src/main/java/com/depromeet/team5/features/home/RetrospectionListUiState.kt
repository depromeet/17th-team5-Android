package com.depromeet.team5.features.home

import androidx.compose.ui.graphics.Color

sealed class RetrospectionListUiState {

    data class Success(
        val symbols: List<RetrospectionSymbolState>
    ): RetrospectionListUiState()

    data object Empty: RetrospectionListUiState()

    data object Loading: RetrospectionListUiState()

    data class Error(
        val code: String,
        val message: String
    ): RetrospectionListUiState()

    data class Failure(
        val throwable: Throwable
    ): RetrospectionListUiState()
}

data class RetrospectionSymbolState(
    val symbol: String,
    val sections: List<RetrospectionSectionState>
)

data class RetrospectionSectionState(
    val title: String,
    val items: List<RetrospectionState>
)

data class RetrospectionState(
    val id: Int,
    val dayText: String,
    val priceVolumeText: String,
    val tradeLabelRes: Int,
    val tradeColor: Color,
    val orderDateText: String
)
