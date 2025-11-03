package com.depromeet.team5.features.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.features.home.RetrospectionSectionState
import com.depromeet.team5.features.home.RetrospectionState
import com.depromeet.team5.features.home.RetrospectionSymbolState
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun RetrospectionMasterDetail(
    symbols: List<RetrospectionSymbolState>,
    modifier: Modifier = Modifier
) {
    var selectedSymbol by rememberSaveable(symbols) {
        mutableStateOf(symbols.firstOrNull()?.symbol)
    }
    val selected = symbols.firstOrNull { it.symbol == selectedSymbol }

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(end = 16.dp)
                .width(118.dp)
        ) {
            items(
                items = symbols,
                key = { item -> item.symbol }
            ) { item ->
                SymbolRailItem(
                    symbol = item.symbol,
                    selected = item.symbol == selectedSymbol,
                    onClick = { selectedSymbol = item.symbol }
                )
            }
        }

        Box(Modifier.weight(1f)) {
            selected?.let {
                RetrospectionDetailList(
                    sections = it.sections,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun SymbolRailItem(
    symbol: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val bg = if (selected) HedgeColor.Neutral.BackgroundSecondary else Color.Transparent

    Row(
        modifier = Modifier
            .padding(bottom = 12.dp, start = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(HedgeColor.GREY_600)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = symbol,
            style = HedgeTypography.Body3.Medium,
            color = if (selected) HedgeColor.Brand.Primary else HedgeColor.Text.Secondary,
            maxLines = 2
        )
    }
}

@Composable
private fun RetrospectionDetailList(
    sections: List<RetrospectionSectionState>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        sections.forEachIndexed { secIndex, section ->
            item(key = "header_${section.title}_$secIndex") {
                Text(
                    text = section.title,
                    style = HedgeTypography.Body3.SemiBold,
                    color = HedgeColor.Text.Primary,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Divider(
                    thickness = 1.dp,
                    color = HedgeColor.Neutral.BackgroundSecondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            val grouped = section.items.groupBy { it.dayText }

            grouped.entries.forEachIndexed { dayIndex, (day, dayItems) ->
                item(key = "day_header_${secIndex}_$dayIndex") {
                    Text(
                        text = day,
                        style = HedgeTypography.Label1.Regular,
                        color = HedgeColor.Text.Primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(
                    items = dayItems,
                    key = { it.id }
                ) { item ->
                    RetrospectionRow(item = item)
                }

                item(key = "day_divider_${secIndex}_$dayIndex") {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            item(key = "divider_$secIndex") {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun RetrospectionRow(
    item: RetrospectionState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Text(
            text = item.priceVolumeText,
            style = HedgeTypography.Headline2.SemiBold,
            color = HedgeColor.Text.Primary
        )

        Row(
            modifier = Modifier.padding(top = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(id = item.tradeLabelRes),
                style = HedgeTypography.Label2.Medium,
                color = item.tradeColor,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = item.orderDateText,
                style = HedgeTypography.Label2.Regular,
                color = HedgeColor.Text.Alternative
            )
        }
    }
}

