package com.depromeet.team5.features.principlegroupdetail.screen

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.depromeet.team5.core.ui.HedgeModal
import com.depromeet.team5.features.principlegroupdetail.R
import com.depromeet.team5.features.principlegroupdetail.event.PrincipleDetailEvent
import kotlin.math.abs


@Composable
fun PrincipleDetailRoute(
    principleType: PrincipleType,
    modifier: Modifier = Modifier,
    viewModel: PrincipleDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    onShowToast: (String) -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.stateFlow.collectAsStateWithLifecycle()

    var isShowModalBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                PrincipleDetailEvent.Finish -> {
                    onBackPressed()
                }

                is PrincipleDetailEvent.ShowErrorToast -> {
                    onShowErrorToast(event.throwable)
                }

                is PrincipleDetailEvent.FinishAndShowToast -> {
                    onShowToast(context.getString(R.string.principle_detail_create_principle_group_toast_message))
                    onBackPressed()
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
            //todo 추후에 수정 기능 연결하기
        },
        onClickedRemoveButton = { groupId ->
            viewModel.deletePrincipleGroup(groupId)
        },
        onClickedItemModifyButton = { principleId ->

        },
        onClickedItemRemoveButton = { principleId ->
            viewModel.deletePrinciple(principleId)
        },
        onClickedConfirmButton = {
            isShowModalBottomSheet = true
        },
        onBackPressed = onBackPressed,
        onShowErrorToast = {
            onBackPressed()
            onShowErrorToast(it)
        }
    )

    if (isShowModalBottomSheet) {
        RecommendedModalBottomSheet(
            onDismissRequest = {
                isShowModalBottomSheet = false
            },
            onClickedButton = { orderType ->
                viewModel.createPrincipleGroup(orderType)
                isShowModalBottomSheet = false
            }
        )
    }
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
    onClickedConfirmButton: () -> Unit,
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit
) {
    var isShowDeleteModal by remember { mutableStateOf(false) }

    if (uiState is HedgeUiState.Loading) {
        LoadingProgressbar()
    }

    when (uiState) {
        is HedgeUiState.Success<MyPrincipleGroup> -> {
            PrincipleDetailContent(
                modifier = modifier,
                myPrincipleGroup = uiState.data,
                principleType = principleType,
                onClickedModifyButton = onClickedModifyButton,
                onClickedItemModifyButton = onClickedItemModifyButton,
                onClickedItemRemoveButton = onClickedItemRemoveButton,
                onBackPressed = onBackPressed,
                onClickedConfirmButton = onClickedConfirmButton,
                onShowDeleteModal = {
                    isShowDeleteModal = true
                }
            )

            if (isShowDeleteModal) {
                HedgeModal(
                    showModal = isShowDeleteModal,
                    title = stringResource(
                        R.string.principle_detail_modal_delete_title,
                        uiState.data.groupName
                    ),
                    description = stringResource(R.string.principle_detail_modal_delete_content),
                    submitButton = stringResource(R.string.delete) to {
                        onClickedRemoveButton(uiState.data.id)
                    },
                    cancelButton = stringResource(R.string.cancel) to {
                        isShowDeleteModal = false
                    },
                    onDismissRequest = {
                        isShowDeleteModal = false
                    },
                    icon = null
                )
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
private fun PrincipleDetailContent(
    modifier: Modifier = Modifier,
    myPrincipleGroup: MyPrincipleGroup,
    principleType: PrincipleType,
    onClickedModifyButton: (Int) -> Unit,
    onClickedItemModifyButton: (Int) -> Unit,
    onClickedItemRemoveButton: (Int) -> Unit,
    onBackPressed: () -> Unit,
    onClickedConfirmButton: () -> Unit,
    onShowDeleteModal: () -> Unit
) {
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
                    onClickedModifyButton(myPrincipleGroup.id)
                },
                onShowDeleteModal = onShowDeleteModal
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
                    if (myPrincipleGroup.thumbnail.startsWith("http")) {
                        AsyncImage(
                            model = myPrincipleGroup.thumbnail,
                            contentDescription = null
                        )
                    } else {
                        Text(
                            text = myPrincipleGroup.thumbnail
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 30.dp),
                    text = myPrincipleGroup.groupName,
                    style = HedgeTypography.Headline1.SemiBold,
                    color = HedgeColor.Text.Title
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                flingBehavior = rememberSlowFlingBehavior(0.5f)
            ) {
                item {
                    Spacer(modifier.size(10.dp))
                }

                itemsIndexed(
                    items = myPrincipleGroup.principles,
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
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClickedConfirmButton = onClickedConfirmButton
                )
            }
        }
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
    modifier: Modifier = Modifier,
    onClickedConfirmButton: () -> Unit
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
                    onClick = onClickedConfirmButton
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
    onShowDeleteModal: () -> Unit
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
                            onShowDeleteModal()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecommendedModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onClickedButton: (OrderType) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        containerColor = HedgeColor.WHITE,
        onDismissRequest = onDismissRequest,
        dragHandle = null
    ) {

        RecommendedModalBottomSheetScreen(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars)
                .fillMaxWidth()
                .heightIn(max = screenHeight * 0.4f),
            onClickedButton = onClickedButton,
            onClickedClose = onDismissRequest
        )
    }
}

@Composable
private fun RecommendedModalBottomSheetScreen(
    modifier: Modifier = Modifier,
    onClickedButton: (OrderType) -> Unit,
    onClickedClose: () -> Unit
) {
    var selectedOrderType by remember { mutableStateOf(OrderType.NONE) }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(HedgeColor.WHITE)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .background(HedgeColor.WHITE),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.principle_detail_modal_bottom_sheet_title),
                    style = HedgeTypography.Body1.SemiBold,
                    color = HedgeColor.Text.Title
                )

                Box(
                    modifier = Modifier
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
                    .clickable(
                        enabled = true,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        selectedOrderType = OrderType.BUY
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(R.string.buy),
                    style = HedgeTypography.Body3.SemiBold,
                    color = HedgeColor.Text.Primary
                )

                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = HedgeIcon.Check,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        if (selectedOrderType == OrderType.BUY) {
                            HedgeColor.Brand.Primary
                        } else {
                            HedgeColor.Text.Disabled
                        }
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
                    .clickable(
                        enabled = true,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        selectedOrderType = OrderType.SELL
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(R.string.sell),
                    style = HedgeTypography.Body3.SemiBold,
                    color = HedgeColor.Text.Primary
                )

                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = HedgeIcon.Check,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        if (selectedOrderType == OrderType.SELL) {
                            HedgeColor.Brand.Primary
                        } else {
                            HedgeColor.Text.Disabled
                        }
                    )
                )
            }

            HedgeButton.Action.Filled(
                modifier = Modifier
                    .padding(start = 20.dp, top = 34.dp, end = 20.dp, bottom = 31.dp)
                    .fillMaxWidth(),
                enabled = selectedOrderType != OrderType.NONE,
                text = stringResource(R.string.select),
                size = HedgeButton.Action.Size.Large,
                onClick = {
                    onClickedButton(selectedOrderType)
                },
            )
        }
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

@Composable
fun rememberSlowFlingBehavior(slowdownFactor: Float = 0.5f): FlingBehavior {
    val flingDecay: DecayAnimationSpec<Float> = rememberSplineBasedDecay()

    return remember(flingDecay, slowdownFactor) {
        object : FlingBehavior {
            override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                if (abs(initialVelocity) < 1f) {
                    return initialVelocity
                }

                var velocityLeft = initialVelocity
                var lastValue = 0f

                AnimationState(
                    initialValue = 0f,
                    initialVelocity = initialVelocity * slowdownFactor,
                ).animateDecay(flingDecay) {
                    val delta = value - lastValue
                    lastValue = value
                    val consumed = scrollBy(delta)

                    if (abs(delta - consumed) > 0.5f) {
                        this.cancelAnimation()
                    }

                    velocityLeft = this.velocity
                }

                return velocityLeft
            }
        }
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
        principleType = PrincipleType.RECOMMENDED,
        onClickedModifyButton = {},
        onClickedRemoveButton = {},
        onClickedItemModifyButton = {},
        onClickedItemRemoveButton = {},
        onClickedConfirmButton = {},
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
        onShowDeleteModal = {}
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
        PrincipleDetailConfirmButton(
            onClickedConfirmButton = {}
        )
    }
}

@Preview
@Composable
fun LoadingProgressbarPreview() {
    LoadingProgressbar()
}

@Preview
@Composable
private fun RecommendedModalBottomSheetScreenPreview() {

    RecommendedModalBottomSheetScreen(
        onClickedButton = {},
        onClickedClose = {}
    )
}