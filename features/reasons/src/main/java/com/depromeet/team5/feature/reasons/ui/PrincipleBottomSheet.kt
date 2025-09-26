package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.feature.reasons.Principle
import com.depromeet.team5.feature.reasons.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipleBottomSheet(
    initialPrinciples: List<Principle>,
    showPrinciplesBottomSheet: Boolean,
    onClickCancel: () -> Unit,
    onClickDone: (List<Principle>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = HedgeColor.Neutral.BackgroundDefault,
    dragHandle: @Composable (() -> Unit)? = null,
) {
    if (showPrinciplesBottomSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            containerColor = containerColor,
            dragHandle = dragHandle,
            modifier = modifier,
        ) {
            PrincipleContent(
                principles = initialPrinciples,
                onClickCancel = onClickCancel,
                onClickDone = onClickDone
            )
        }
    }
}

@Composable
fun PrincipleContent(
    principles: List<Principle>,
    onClickCancel: () -> Unit,
    onClickDone: (List<Principle>) -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var ctaHeightPx by remember { mutableIntStateOf(0) }
    val internalPrinciples = remember(principles) { principles.toMutableStateList() }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 22.dp, end = 10.dp, top = 10.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(R.string.investment_principle),
                    color = HedgeColor.Text.Title,
                    style = HedgeTypography.Headline2.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "close",
                    contentScale = ContentScale.None,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable(
                            onClick = onClickCancel,
                            interactionSource = remember { MutableInteractionSource() },
                        )
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(bottom = with(density) { ctaHeightPx.toDp() })
            ) {
                items(
                    items = internalPrinciples,
                    key = { item -> item.id }
                ) { item ->
                    PrincipleItem(
                        principle = item,
                        onClickItem = {
                            val idx = internalPrinciples.indexOfFirst { it.id == item.id }
                            if (idx != -1) {
                                internalPrinciples[idx] =
                                    internalPrinciples[idx].copy(checked = !internalPrinciples[idx].checked)
                            }
                        }
                    )
                    HorizontalDivider(
                        color = HedgeColor.Neutral.BackgroundSecondary,
                        thickness = 1.dp
                    )
                }
            }
        }

        HedgeButton.CallToAction.Single(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .onSizeChanged { ctaHeightPx = it.height },
            text = stringResource(R.string.record),
            background = HedgeButton.CallToAction.Background.Gradient(HedgeColor.Neutral.BackgroundDefault),
            onClick = {
                onClickDone(internalPrinciples.toList())
            },
        )
    }
}

@Composable
fun PrincipleItem(
    principle: Principle,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = onClickItem,
                interactionSource = remember { MutableInteractionSource() },
            )
            .padding(horizontal = 24.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_principle_logo),
            contentDescription = "principle logo",
        )

        Spacer(Modifier.size(16.dp))

        Text(
            text = principle.description,
            modifier = Modifier.weight(1f),
            style = HedgeTypography.Body3.SemiBold,
            color = HedgeColor.GREY_900
        )

        Spacer(Modifier.size(24.dp))

        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    color = HedgeColor.Neutral.BackgroundSecondary,
                )
        ) {
            if (principle.checked) {
                Image(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize()
                        .align(Alignment.Center)
                        .background(color = HedgeColor.Brand.Primary)
                )
            }
        }
    }
}

private val items = mutableStateListOf(
    Principle(1, "안전마진을 확보하라1"),
    Principle(2, "안전마진을 확보하라2", true),
    Principle(3, "안전마진을 확보하라3"),
    Principle(4, "안전마진을 확보하라4"),
    Principle(5, "안전마진을 확보하라5"),
    Principle(6, "안전마진을 확보하라6"),
    Principle(7, "안전마진을 확보하라7"),
    Principle(8, "안전마진을 확보하라8"),
    Principle(9, "안전마진을 확보하라9"),
    Principle(10, "안전마진을 확보하라10"),
)


@Composable
@Preview(showBackground = true)
private fun ContentPreview() {
    PrincipleContent(
        principles = items,
        onClickCancel = {},
        onClickDone = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun PrincipleItemPreview() {
    PrincipleItem(
        principle = items[0],
        onClickItem = {},
    )
}

@Composable
@Preview
private fun PrincipleBottomSheetPreview() {
    PrincipleBottomSheet(
        initialPrinciples = items,
        showPrinciplesBottomSheet = true,
        onClickCancel = {},
        onClickDone = {},
        onDismissRequest = {}
    )
}