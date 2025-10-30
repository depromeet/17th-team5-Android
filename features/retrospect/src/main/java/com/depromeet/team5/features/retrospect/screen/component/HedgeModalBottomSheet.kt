package com.depromeet.team5.features.retrospect.screen.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.features.retrospect.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HedgeModalBottomSheet(
    myPrincipleMap: Map<String, List<MyPrinciple>>,
    onClickedClose: () -> Unit,
    onClickedConfirmButton: (List<MyPrinciple>) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    LaunchedEffect(Unit) {
        sheetState.expand()
    }

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = { onClickedClose() },
        containerColor = HedgeColor.WHITE,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) }

    ) {
        Box(
            modifier = Modifier
                .heightIn(max = (screenHeight * 0.85f))
        ) {
            HedgeModalBottomSheetScreen(
                myPrincipleMap = myPrincipleMap,
                onClickedClose = onClickedClose,
                onClickedConfirmButton = { list ->
                    scope.launch {
                        sheetState.hide()
                    }

                    onClickedConfirmButton(list)
                }
            )
        }
    }
}

@Composable
private fun HedgeModalBottomSheetScreen(
    myPrincipleMap: Map<String, List<MyPrinciple>>,
    onClickedClose: () -> Unit,
    onClickedConfirmButton: (List<MyPrinciple>) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMyPrincipleItem by remember { mutableStateOf("") }

    val springSpec = spring<IntSize>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 23.5.dp, bottom = 19.5.dp),
                    text = stringResource(R.string.principle_bottom_sheet_dialog_title),
                    style = HedgeTypography.Body1.SemiBold,
                    color = HedgeColor.GREY_900
                )

                Box(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(28.dp)
                        .background(
                            color = HedgeColor.Neutral.BackgroundSecondary,
                            shape = RoundedCornerShape(39.dp)
                        )
                        .clickable(true) { onClickedClose() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(14.dp),
                        imageVector = HedgeIcon.CloseThick,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = HedgeColor.Text.Assistive)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = stringResource(R.string.standard),
                    modifier = Modifier.padding(start = 20.dp),
                    style = HedgeTypography.Label2.Medium,
                    color = HedgeColor.Text.Alternative
                )

                Spacer(modifier = Modifier.size(4.dp))

                HedgePrincipleListItem(
                    selected = false,
                    icon = {},
                    title = "초보자를 위한 매도 원칙"
                )

                HedgePrincipleListItem(
                    icon = {},
                    title = "초보자를 위한 매수 원칙"
                )
            }

            Text(
                text = stringResource(R.string.principle_myself),
                modifier = Modifier.padding(start = 20.dp, top = 12.dp),
                style = HedgeTypography.Label2.Medium,
                color = HedgeColor.Text.Alternative
            )

            Spacer(modifier = Modifier.size(4.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = myPrincipleMap.keys.toList(),
                    key = { it }
                ) { key ->
                    HedgePrincipleListItem(
                        modifier = Modifier
                            .clickable(
                                enabled = true,
                                indication = null,
                                interactionSource = interactionSource
                            ) { selectedMyPrincipleItem = key },
                        title = key,
                        icon = {},
                        selected = key == selectedMyPrincipleItem
                    )

                    AnimatedVisibility(
                        key == selectedMyPrincipleItem,
                        enter = expandVertically(
                            animationSpec = springSpec,
                            expandFrom = Alignment.Top
                        ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                        // 사라질 때: 아래에서 위로 줄어들며 서서히 사라짐
                        exit = shrinkVertically(
                            animationSpec = springSpec,
                            shrinkTowards = Alignment.Top
                        ) + fadeOut(animationSpec = tween(durationMillis = 300))
                    ) {
                        Row {
                            Spacer(modifier = Modifier.size(36.dp))

                            Row(
                                modifier = Modifier.height(IntrinsicSize.Min)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(3.dp)
                                        .fillMaxHeight()
                                        .background(HedgeColor.Neutral.BackgroundSecondary)
                                )

                                Spacer(modifier = Modifier.size(24.dp))

                                Column {
                                    val list = myPrincipleMap.getValue(key)

                                    list.forEachIndexed { index, item ->
                                        SelectedMyPrincipleItem(item)

                                        if (index != list.size - 1)
                                            Spacer(modifier = Modifier.size(12.dp))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }

            if (selectedMyPrincipleItem.isNotEmpty()) {
                HedgeButton.Action.Filled(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 31.dp),
                    enabled = selectedMyPrincipleItem.isNotEmpty(),
                    text = stringResource(id = R.string.principle_bottom_sheet_dialog_button_text),
                    onClick = {
                        onClickedConfirmButton(
                            myPrincipleMap.getValue(selectedMyPrincipleItem)
                        )
                    }
                )
            }
        }
    }
}


@Composable
private fun HedgePrincipleListItem(
    title: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .background(HedgeColor.WHITE),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = HedgeColor.Neutral.BackgroundSecondary,
                    shape = RoundedCornerShape(39.dp)
                )
        ) {
            icon()
        }

        Text(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f),
            text = title,
            style = if (selected) HedgeTypography.Body3.SemiBold else HedgeTypography.Body3.Medium,
            color = if (selected) HedgeColor.Brand.Primary else HedgeColor.Text.Title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Image(
            modifier = Modifier.size(24.dp),
            imageVector = if (selected) HedgeIcon.Check else HedgeIcon.ArrowRightThin,
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = if (selected) HedgeColor.Brand.Primary else HedgeColor.Text.Disabled
            )
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Preview
@Composable
fun HedgeModalBottomSheetPreview() {
    val myPrincipleMap: Map<String, List<MyPrinciple>> = mapOf(
        "이건 좀 지키자 제발" to listOf(
            MyPrinciple(
                id = 1,
                groupId = 1,
                principle = "안전마진을 확보하라"
            ),
            MyPrinciple(
                id = 2,
                groupId = 1,
                principle = "기업의 본질 가치보다 낮게 거래되는 주식을 찾아 장기 보유하기"
            ),
            MyPrinciple(
                id = 3,
                groupId = 1,
                principle = "정책 민감도가 높은 주식은 정책 잘 살펴보고 매매"
            ),
        ),
        "이건 좀 지키자 제발2" to listOf(
            MyPrinciple(
                id = 1,
                groupId = 2,
                principle = "안전마진을 확보하라"
            ),
            MyPrinciple(
                id = 2,
                groupId = 2,
                principle = "기업의 본질 가치보다 낮게 거래되는 주식을 찾아 장기 보유하기"
            ),
            MyPrinciple(
                id = 3,
                groupId = 2,
                principle = "정책 민감도가 높은 주식은 정책 잘 살펴보고 매매"
            ),
        )
    )

    HedgeModalBottomSheetScreen(
        myPrincipleMap = myPrincipleMap,
        onClickedClose = {},
        onClickedConfirmButton = {}
    )
}


@Composable
fun SelectedMyPrincipleItem(
    item: MyPrinciple
) {
    Row {
        Text(
            text = item.id.toString(),
            color = HedgeColor.Brand.Primary,
            style = HedgeTypography.Body3.SemiBold
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = item.principle,
            style = HedgeTypography.Body3.Regular,
            color = HedgeColor.Text.Primary,
        )
    }
}

@Preview
@Composable
fun HedgePrincipleListItemPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(HedgeColor.WHITE)
    ) {
        HedgePrincipleListItem(
            title = "초보자를 위한 매도 원칙",
            icon = {

            }
        )
    }
}

@Preview
@Composable
fun SelectedMyPrincipleItemPreview() {
    val myPrinciple = MyPrinciple(
        id = 1,
        groupId = 1,
        principle = "안전마진을 확보하라"
    )

    Box(
        modifier = Modifier.background(HedgeColor.WHITE)
    ) {
        SelectedMyPrincipleItem(myPrinciple)
    }
}