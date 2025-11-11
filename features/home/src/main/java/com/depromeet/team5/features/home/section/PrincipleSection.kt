package com.depromeet.team5.features.home.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.DefaultPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.RecommendedPrinciple
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.navigation.Path
import com.depromeet.team5.core.ui.component.HedgeLoadingScreen
import com.depromeet.team5.features.home.R
import com.depromeet.team5.features.home.component.OrderTypeButton
import com.depromeet.team5.features.home.component.PrincipleItem
import com.depromeet.team5.features.home.component.RecommendPrincipleItem

@Composable
fun PrincipleSection(
    recommendedUiState: HedgeUiState<List<RecommendedPrinciple>>,
    defaultsUiState: HedgeUiState<List<DefaultPrinciple>>,
    selected: OrderType,
    onSelect: (OrderType) -> Unit,
    principleGroupsUiState: HedgeUiState<List<MyPrincipleGroup>>,
    onClickPrincipleDetail: (Int, Path) -> Unit,
    onClickCreatePrinciple: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interaction = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {

        when (val recommendPrinciple = recommendedUiState) {
            is HedgeUiState.Success -> {
                LazyRow(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = recommendPrinciple.data,
                        key = { it.id }
                    ) {
                        RecommendPrincipleItem(
                            recommendPrinciple = it,
                            onClick = { groupId ->
                                onClickPrincipleDetail(groupId, Path.PRINCIPLE_RECOMMENDED)
                            },
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }
            }

            is HedgeUiState.Loading -> {

            }

            is HedgeUiState.Error -> {
                recommendPrinciple.throwable?.let {
                    onShowErrorToast(it)
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

        when (val defaultPrinciple = defaultsUiState) {
            is HedgeUiState.Success -> {
                val filtered = defaultPrinciple.data.filter { it.orderType == selected }
                filtered.forEach { group ->
                    PrincipleItem(
                        id = group.id,
                        icon = group.thumbnail,
                        title = group.groupName,
                        onClick = {
                            onClickPrincipleDetail(
                                group.id,
                                Path.PRINCIPLE_SYSTEM
                            )
                        }
                    )
                }

            }

            is HedgeUiState.Loading -> {
                HedgeLoadingScreen()
            }

            is HedgeUiState.Error -> {
                defaultPrinciple.throwable?.let {
                    onShowErrorToast(it)
                }
            }
        }

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
                    .clickable(
                        interactionSource = interaction,
                        indication = null
                    ) {
                        onClickCreatePrinciple()
                    }
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
                        id = group.id,
                        icon = group.thumbnail,
                        title = group.groupName,
                        onClick = {
                            onClickPrincipleDetail(
                                group.id,
                                Path.PRINCIPLE_MINE
                            )
                        }
                    )
                }
            }

            is HedgeUiState.Loading -> {
                HedgeLoadingScreen()
            }

            is HedgeUiState.Error -> {
                ui.throwable?.let {
                    onShowErrorToast(it)
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PrincipleSectionPreview() {
    PrincipleSection(
        recommendedUiState = HedgeUiState.Loading(emptyList()),
        defaultsUiState = HedgeUiState.Loading(emptyList()),
        selected = OrderType.BUY,
        onSelect = {},
        principleGroupsUiState = HedgeUiState.Loading(emptyList()),
        onClickPrincipleDetail = { _, _ -> },
        onClickCreatePrinciple = {},
        onShowErrorToast = {}
    )
}