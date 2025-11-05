package com.depromeet.team5.features.home.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.features.home.R
import com.depromeet.team5.features.home.component.OrderTypeButton
import com.depromeet.team5.features.home.component.PrincipleItem
import com.depromeet.team5.features.home.component.RecommendPrincipleItem
import com.depromeet.team5.features.home.screen.RecommendPrinciple

@Composable
fun PrincipleSection(
    selected: OrderType,
    onSelect: (OrderType) -> Unit,
    principleGroupsUiState: HedgeUiState<List<MyPrincipleGroup>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        LazyRow(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RecommendPrinciple.entries.forEach {
                item {
                    RecommendPrincipleItem(
                        recommendPrinciple = it,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            OrderTypeButton(
                title = OrderType.BUY.toKorean(),
                isSelected = (selected == OrderType.BUY),
                onClick = { onSelect(OrderType.BUY) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            OrderTypeButton(
                title = OrderType.SELL.toKorean(),
                isSelected = (selected == OrderType.SELL),
                onClick = { onSelect(OrderType.SELL) },
            )
        }
        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.principle_tab_default_title),
            style = HedgeTypography.Headline2.SemiBold,
            color = HedgeColor.Text.Title,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )

        Divider(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = HedgeColor.Neutral.BackgroundSecondary
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.principle_tab_my_title),
                style = HedgeTypography.Headline2.SemiBold,
                color = HedgeColor.Text.Title,
            )

            Image(
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = null,
                modifier = Modifier
                    .clickable {}
                    .background(
                        color = HedgeColor.Brand.Primary,
                        shape = CircleShape
                    )
                    .padding(9.dp)
                    .size(10.dp)
            )
        }

        when (val ui = principleGroupsUiState) {
            is HedgeUiState.Success -> {
                ui.data.forEach { group ->
                    PrincipleItem(
                        icon = group.thumbnail,
                        title = group.groupName,
                    )
                }

            }

            is HedgeUiState.Loading -> {

            }

            is HedgeUiState.Error -> {

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PrincipleSectionPreview() {
    PrincipleSection(
        selected = OrderType.BUY,
        onSelect = {},
        principleGroupsUiState = HedgeUiState.Loading(emptyList())
    )
}