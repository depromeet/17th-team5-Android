package com.depromeet.team5.features.principledetail.screen

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.navigation.PrincipleType
import com.depromeet.team5.features.principledetail.R
import com.depromeet.team5.features.principledetail.event.PrincipleDetailEvent


@Composable
fun PrincipleDetailRoute(
    principleType: PrincipleType,
    modifier: Modifier = Modifier,
    viewModel: PrincipleDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                PrincipleDetailEvent.Finish -> {
                    onBackPressed()
                }

                is PrincipleDetailEvent.ShowErrorToast -> {
                    onShowErrorToast(event.throwable)
                }

                else -> {}
            }

        }
    }

    PrincipleDetailScreen(
        modifier = modifier,
        uiState = uiState,
        principleType = principleType,
        onClickedModifyButton = { groupId ->

        },
        onClickedRemoveButton = { groupId ->
            viewModel.deletePrincipleGroup(groupId)
        },
        onClickedItemModifyButton = { principleId ->

        },
        onClickedItemRemoveButton = { principleId ->
            viewModel.deletePrinciple(principleId)
        },
        onBackPressed = onBackPressed,
        onShowErrorToast = {
            onBackPressed()
            onShowErrorToast(it)
        }
    )
}

@Composable
private fun PrincipleDetailScreen(
    uiState: HedgeUiState<MyPrincipleGroup>,
    principleType: PrincipleType,
    modifier: Modifier = Modifier,
    onClickedModifyButton: (Int) -> Unit,
    onClickedRemoveButton: (Int) -> Unit,
    onClickedItemModifyButton: (Int) -> Unit,
    onClickedItemRemoveButton: (Int) -> Unit,
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit
) {
    if (uiState is HedgeUiState.Loading) {
        LoadingProgressbar()
    }

    when (uiState) {
        is HedgeUiState.Success<MyPrincipleGroup> -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(HedgeColor.WHITE)
                ) {
                    Topbar(
                        modifier = Modifier.background(HedgeColor.Brand.Secondary),
                        onBackPressed = onBackPressed,
                        onClickedModifyButton = {
                            onClickedModifyButton(uiState.data.id)
                        },
                        onClickedRemoveButton = {
                            onClickedRemoveButton(uiState.data.id)
                        }
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(HedgeColor.Brand.Secondary)
                            .padding(horizontal = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 44.dp)
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(HedgeColor.WHITE),
                            contentAlignment = Alignment.Center
                        ) {
                            if (uiState.data.thumbnail.startsWith("http")) {
                                AsyncImage(
                                    model = uiState.data.thumbnail,
                                    contentDescription = null
                                )
                            } else {
                                Text(
                                    text = uiState.data.thumbnail
                                )
                            }
                        }

                        Text(
                            modifier = Modifier.padding(top = 20.dp, bottom = 30.dp),
                            text = uiState.data.groupName,
                            style = HedgeTypography.Headline1.SemiBold,
                            color = HedgeColor.Text.Title
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        item {
                            Spacer(modifier.size(10.dp))
                        }

                        itemsIndexed(
                            items = uiState.data.principles,
                            key = { index, principle -> principle.id }
                        ) { index, principle ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem(
                                        fadeInSpec = TweenSpec(
                                            durationMillis = 500
                                        ),
                                        fadeOutSpec = TweenSpec(
                                            durationMillis = 500
                                        ),
                                        placementSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                            ) {
                                PrincipleItem(
                                    index = index + 1,
                                    principle = principle,
                                    onClickedItemModifyButton = onClickedItemModifyButton,
                                    onClickedItemRemoveButton = onClickedItemRemoveButton
                                )
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = HedgeColor.Neutral.BackgroundSecondary
                                )
                            }
                        }
                    }
                }

                when (principleType) {
                    PrincipleType.MINE -> {
                        PrincipleDetailFloatingButton(
                            modifier = Modifier
                                .padding(end = 20.dp, bottom = 45.dp)
                                .align(alignment = Alignment.BottomEnd)
                        ) {
                            //todo 버튼 클릭 시 원칙 추가 기능 넣기
                        }
                    }

                    PrincipleType.RECOMMENDED -> {
                        PrincipleDetailConfirmButton(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }

        is HedgeUiState.Error -> {
            uiState.throwable?.let {
                onShowErrorToast(it)
            }
        }

        else -> {}
    }
}

@Composable
fun PrincipleItem(
    index: Int,
    principle: MyPrinciple,
    modifier: Modifier = Modifier,
    onClickedItemModifyButton: (Int) -> Unit,
    onClickedItemRemoveButton: (Int) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(HedgeColor.WHITE)
            .padding(start = 20.dp, top = 20.dp, end = 10.dp, bottom = 20.dp),
    ) {
        Text(
            text = index.toString(),
            style = HedgeTypography.Body3.SemiBold,
            color = HedgeColor.Brand.Primary
        )

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
        ) {
            Text(
                modifier = Modifier,
                text = principle.principle,
                style = HedgeTypography.Body2.SemiBold,
                color = HedgeColor.Text.Primary
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = principle.description,
                style = HedgeTypography.Body3.Regular,
                color = HedgeColor.Text.Primary
            )
        }

        Box {
            Image(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable(
                        enabled = true,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        isExpanded = !isExpanded
                    },
                imageVector = HedgeIcon.Menu,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = HedgeColor.Text.Disabled)
            )

            ModifyAndRemoveDropdown(
                expand = isExpanded,
                onClickedModifyButton = {
                    onClickedItemModifyButton(principle.id)
                    isExpanded = false
                },
                onClickedRemoveButton = {
                    onClickedItemRemoveButton(principle.id)
                    isExpanded = false
                },
                onDismissRequest = { isExpanded = false }
            )
        }
    }
}

@Composable
fun PrincipleDetailConfirmButton(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()

                    drawRect(
                        brush = Brush
                            .verticalGradient(
                                colorStops = arrayOf(
                                    0f to HedgeColor.WHITE.copy(0f),
                                    0.5f to HedgeColor.WHITE.copy(0.98f)
                                ),
                                startY = 0f,
                                endY = 24.dp.toPx()
                            ),
                        size = Size(
                            width = this.size.width,
                            height = 24.dp.toPx()
                        )
                    )

                }
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)

            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HedgeColor.WHITE)
            ) {
                HedgeButton.Action.Filled(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    text = "내 회고 템플릿에 추가하기",
                    size = HedgeButton.Action.Size.Large,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun PrincipleDetailFloatingButton(
    modifier: Modifier = Modifier,
    onClickedButton: () -> Unit
) {
    Box(
        modifier = modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(color = HedgeColor.Brand.Primary)
            .clickable(true) {
                onClickedButton()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            imageVector = HedgeIcon.Add,
            contentDescription = null,
            colorFilter = ColorFilter.tint(HedgeColor.WHITE)
        )
    }
}

@Composable
private fun Topbar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onClickedModifyButton: () -> Unit,
    onClickedRemoveButton: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable(enabled = true) { onBackPressed() },
                imageVector = HedgeIcon.ArrowLeftThick,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = HedgeColor.Text.Primary)
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Box {
                Image(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable(
                            enabled = true,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            isExpanded = !isExpanded
                        },
                    imageVector = HedgeIcon.Menu,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = HedgeColor.Text.Primary)
                )
                if (isExpanded) {
                    ModifyAndRemoveDropdown(
                        expand = isExpanded,
                        onClickedModifyButton = {
                            onClickedModifyButton()
                            isExpanded = false
                        },
                        onClickedRemoveButton = {
                            onClickedRemoveButton()
                            isExpanded = false
                        },
                        onDismissRequest = { isExpanded = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun ModifyAndRemoveDropdown(
    expand: Boolean,
    modifier: Modifier = Modifier,
    onClickedModifyButton: () -> Unit,
    onClickedRemoveButton: () -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        modifier = modifier
            .background(HedgeColor.WHITE),
        shape = RoundedCornerShape(16.dp),
        expanded = expand,
        containerColor = HedgeColor.WHITE,
        onDismissRequest = onDismissRequest,
        offset = DpOffset((-20).dp, 0.dp)
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.modify),
                    style = HedgeTypography.Body3.Medium,
                    color = HedgeColor.Text.Primary
                )
            },
            onClick = onClickedModifyButton,
            trailingIcon = {
                Image(
                    imageVector = HedgeIcon.Pencil,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(HedgeColor.Text.Primary)
                )
            }
        )
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.delete),
                    style = HedgeTypography.Body3.Medium,
                    color = HedgeColor.Feedback.Error
                )
            },
            onClick = onClickedRemoveButton,
            trailingIcon = {
                Image(
                    imageVector = HedgeIcon.Trash,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(HedgeColor.Feedback.Error)
                )
            }
        )
    }
}

@Composable
private fun LoadingProgressbar() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = 2.5.dp,
            color = HedgeColor.Brand.Primary,
            strokeCap = StrokeCap.Round,
            trackColor = HedgeColor.Brand.Primary.copy(alpha = 0.1f),
        )
    }
}


@Preview
@Composable
fun PrincipleDetailScreenPreview() {
    val list = mutableListOf<MyPrinciple>()

    for (i in 0 until 20) {
        list.add(
            MyPrinciple.EMPTY.copy(
                id = i,
                principle = "종목 선택 시 최근 매출액 확인하기 종목 선택 시 최근 매출액 확인하기 ",
                description = "상승장에서 눌림목 나오면 지지선 나올 때까지 기다렸다가 분할 매수하자. 몰빵은 절대 금지."
            )
        )
    }

    PrincipleDetailScreen(
        uiState = HedgeUiState.Success(
            data = MyPrincipleGroup.EMPTY.copy(
                groupName = "이건 좀 지키자 제발",
                orderType = OrderType.BUY,
                principles = list
            )
        ),
        principleType = PrincipleType.MINE,
        onClickedModifyButton = {},
        onClickedRemoveButton = {},
        onClickedItemModifyButton = {},
        onClickedItemRemoveButton = {},
        onBackPressed = {},
        onShowErrorToast = {}
    )
}


@Preview(showBackground = true)
@Composable
fun TopbarPreview() {
    Topbar(
        modifier = Modifier
            .background(HedgeColor.Brand.Secondary),
        onBackPressed = {},
        onClickedModifyButton = {},
        onClickedRemoveButton = {}
    )
}

@Preview
@Composable
fun PrincipleItemPreview() {
    val principle = MyPrinciple.EMPTY.copy(
        principle = "종목 선택 시 최근 매출액 확인하기 종목 선택 시 최근 매출액 확인하기 ",
        description = "상승장에서 눌림목 나오면 지지선 나올 때까지 기다렸다가 분할 매수하자. 몰빵은 절대 금지."
    )

    PrincipleItem(
        index = 1,
        principle = principle,
        onClickedItemModifyButton = {},
        onClickedItemRemoveButton = {}
    )
}


@Preview
@Composable
fun PrincipleDetailFloatingButtonPreview() {
    PrincipleDetailFloatingButton(
        onClickedButton = {}
    )
}

@Preview
@Composable
fun ConfirmButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(HedgeColor.WHITE)
    ) {
        PrincipleDetailConfirmButton()
    }
}

@Preview
@Composable
fun LoadingProgressbarPreview() {
    LoadingProgressbar()
}
