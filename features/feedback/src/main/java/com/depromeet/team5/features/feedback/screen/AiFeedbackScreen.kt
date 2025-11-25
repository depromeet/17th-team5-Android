package com.depromeet.team5.features.feedback.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.toOrderType
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.core.ui.component.HedgeCompanyLogo
import com.depromeet.team5.core.ui.component.HedgeLoadingScreen
import com.depromeet.team5.core.ui.component.PrincipleBottomSheetDialog
import com.depromeet.team5.core.ui.model.HedgeBadge
import com.depromeet.team5.features.feedback.AiFeedbackUiState
import com.depromeet.team5.features.feedback.PrincipleState
import com.depromeet.team5.features.feedback.R
import com.depromeet.team5.features.feedback.component.PrincipleCounter
import com.depromeet.team5.features.feedback.event.Event
import kotlinx.coroutines.launch
import com.depromeet.team5.core.ui.R as UiR


@Composable
fun AiFeedbackRoute(
    requestViewModel: RequestViewModel,
    onBack: () -> Unit,
    onCompleteClick: (Int) -> Unit,
    retrospectionId: Int? = null,
    modifier: Modifier = Modifier,
    onShowToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    viewModel: AiFeedbackViewModel = hiltViewModel()
) {
    val uiState by viewModel.feedbackStateFlow.collectAsStateWithLifecycle()
    val isCreateMode = retrospectionId == null

    var isShowPrincipleModal by remember { mutableStateOf(false) }

    LaunchedEffect(retrospectionId) {
        if (retrospectionId != null) {
            viewModel.loadFeedback(retrospectionId)
        } else {
            requestViewModel.selectedMyPrincipleGroupState?.let {
                viewModel.createRetrospection(requestViewModel.request, it)
            }
        }
    }

    val successId = (uiState as? AiFeedbackUiState.Success)?.retrospectionId
    BackHandler(enabled = isCreateMode && successId != null) {
        onCompleteClick(successId!!)
    }

    when (val state = uiState) {
        is AiFeedbackUiState.Success -> {
            AiFeedbackScreen(
                state = state,
                grade = HedgeBadge.fromBadge(state.badge),
                companyLogo = requestViewModel.companyLogoUrl,
                companyName = requestViewModel.request.companyName,
                price = requestViewModel.request.price.toLong(),
                stock = requestViewModel.request.volume,
                isCreateMode = isCreateMode,
                onBack = onBack,
                modifier = modifier.windowInsetsPadding(WindowInsets.systemBars),
                onCompleteClick = { onCompleteClick(state.retrospectionId) },
                onClickedPrincipleAddButton = {
                    isShowPrincipleModal = true
                }
            )
        }

        is AiFeedbackUiState.Loading -> {
            HedgeLoadingScreen()
        }

        is AiFeedbackUiState.Error -> {
            onShowErrorToast(Throwable("${state.code}, ${state.message}"))
            onBack()
        }

        is AiFeedbackUiState.Failure -> {
            onShowErrorToast(state.throwable)
            onBack()
        }
    }

    if (isShowPrincipleModal) {
        PrincipleDialog(
            orderType = requestViewModel.request.orderType,
            onClickedConfirmButton = {
                isShowPrincipleModal = false
            },
            onClickedAddButton = {
                isShowPrincipleModal = false
            },
            onClickedClose = {
                isShowPrincipleModal = false
            },
            onShowErrorToast = onShowErrorToast
        )
    }
}

@Composable
private fun AiFeedbackScreen(
    state: AiFeedbackUiState.Success,
    grade: HedgeBadge,
    companyLogo: String?,
    companyName: String,
    price: Long,
    stock: Int,
    isCreateMode: Boolean,
    modifier: Modifier = Modifier,
    onClickedPrincipleAddButton: () -> Unit,
    onCompleteClick: () -> Unit,
    onBack: () -> Unit,
) {
    val gradientGreenBlue = Brush.linearGradient(
        colors = listOf(Color(0xFF07BC70), Color(0xFF0696BE))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HedgeColor.Neutral.BackgroundSecondary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(422.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Color(0xFFF3F4F6),
                            0.75f to Color(0xFFFFFFFF),
                            1.0f to Color(0xFFFFFFFF).copy(alpha = 0f)
                        )
                    )
                )
                .align(Alignment.TopCenter)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp)
                .height(462.dp)
                .align(Alignment.TopCenter)
                .drawWithCache {
                    val centerColor = Color(0xFF1CCAFF).copy(alpha = 0.24f)
                    val edgeColor = Color(0xFF1CCAFF).copy(alpha = 0f)

                    val radius = size.height / 2f
                    val center = Offset(x = size.width * 0.85f, y = size.height * 0.5f)

                    val brush = Brush.radialGradient(
                        colors = listOf(centerColor, edgeColor),
                        center = center,
                        radius = radius
                    )
                    onDrawBehind { drawRect(brush) }
                }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp)
                .height(462.dp)
                .align(Alignment.TopCenter)
                .drawWithCache {
                    val centerColor = Color(0xFF29F980).copy(alpha = 0.16f)
                    val edgeColor = Color(0xFF29F980).copy(alpha = 0f)

                    val radius = size.height / 2f
                    val center = Offset(x = size.width * 0.15f, y = size.height * 0.5f)

                    val brush = Brush.radialGradient(
                        colors = listOf(centerColor, edgeColor),
                        center = center,
                        radius = radius
                    )
                    onDrawBehind { drawRect(brush) }
                }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                if (isCreateMode){
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.feedback_complete),
                            style = HedgeTypography.Body1.SemiBold,
                            color = HedgeColor.Brand.Darken,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onCompleteClick() }
                        )
                    }
                }else{
                    HedgeTopBar(onClickBack = onBack)
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(grade.iconRes),
                        contentDescription = state.badge,
                        modifier = Modifier.size(111.dp, 130.dp)
                    )

                    Text(
                        text = stringResource(grade.titleRes),
                        style = HedgeTypography.Headline1.SemiBold.copy(
                            brush = gradientGreenBlue
                        ),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )

                    Text(
                        text = stringResource(grade.descriptionRes),
                        style = HedgeTypography.Label1.Medium,
                        color = HedgeColor.Text.Secondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(187.dp)
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .background(
                            color = HedgeColor.WHITE,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 22.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HedgeCompanyLogo(
                            logoUrl = companyLogo,
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(28.dp)
                        )

                        Column(
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = state.companyName,
                                style = HedgeTypography.Label2.Medium,
                                color = HedgeColor.Text.Alternative
                            )

                            Text(
                                text = stringResource(
                                    R.string.price_and_stock,
                                    state.price,
                                    state.volume,
                                    state.orderType.toOrderType().toKorean()
                                ),
                                style = HedgeTypography.Body2.SemiBold,
                                color = HedgeColor.Text.Primary
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                            .height(IntrinsicSize.Min),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        PrincipleCounter(
                            iconRes = R.drawable.ic_kept,
                            count = state.principleCheckSummary.keptCount
                        )

                        Box(
                            modifier = modifier
                                .padding(horizontal = 37.dp)
                                .width(1.dp)
                                .fillMaxHeight()
                                .padding(vertical = 9.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(HedgeColor.Neutral.BackgroundSecondary)
                        )

                        PrincipleCounter(
                            iconRes = R.drawable.ic_neutral,
                            count = state.principleCheckSummary.neutralCount
                        )

                        Box(
                            modifier = modifier
                                .padding(horizontal = 37.dp)
                                .width(1.dp)
                                .fillMaxHeight()
                                .padding(vertical = 9.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(HedgeColor.Neutral.BackgroundSecondary)
                        )

                        PrincipleCounter(
                            iconRes = R.drawable.ic_notkept,
                            count = state.principleCheckSummary.notKeptCount
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .background(
                            color = HedgeColor.WHITE,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.feedback_keep_title),
                        style = HedgeTypography.Body1.SemiBold.copy(
                            brush = gradientGreenBlue
                        ),
                        modifier = Modifier.padding(bottom = 9.dp)
                    )

                    state.keep.forEach { text ->
                        Text(
                            text = stringResource(R.string.feedback_content, text),
                            style = HedgeTypography.Body3.Medium,
                            color = HedgeColor.Text.Primary,
                            modifier = Modifier.padding(top = 7.dp)
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .background(
                            color = HedgeColor.WHITE,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.feedback_fix_title),
                        style = HedgeTypography.Body1.SemiBold.copy(
                            brush = gradientGreenBlue
                        ),
                        modifier = Modifier.padding(bottom = 9.dp)
                    )

                    state.fix.forEach { text ->
                        Text(
                            text = stringResource(R.string.feedback_content, text),
                            style = HedgeTypography.Body3.Medium,
                            color = HedgeColor.Text.Primary,
                            modifier = Modifier.padding(top = 7.dp)
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .background(
                            color = HedgeColor.WHITE,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.feedback_next_title),
                        style = HedgeTypography.Body1.SemiBold.copy(
                            brush = gradientGreenBlue
                        ),
                        modifier = Modifier.padding(bottom = 9.dp)
                    )

                    state.keep.forEach { text ->
                        Text(
                            text = stringResource(R.string.feedback_content, text),
                            style = HedgeTypography.Body3.Medium,
                            color = HedgeColor.Text.Primary,
                            modifier = Modifier.padding(top = 7.dp)
                        )
                    }

                    HedgeButton.Action.Filled(
                        text = stringResource(R.string.feedback_add_principle_button),
                        buttonColors = HedgeButton.Action.Color.Filled.Primary,
                        size = HedgeButton.Action.Size.Small,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        onClick = onClickedPrincipleAddButton
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp, bottom = 121.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_notice),
                        contentDescription = null,
                        tint = HedgeColor.Text.Alternative,
                        modifier = Modifier.padding(end = 10.dp)
                    )

                    Text(
                        text = stringResource(R.string.feedback_ai_notice),
                        style = HedgeTypography.Label2.Regular,
                        color = HedgeColor.Text.Alternative
                    )
                }
            }
        }
    }
}

@Composable
private fun PrincipleDialog(
    orderType: OrderType,
    modifier: Modifier = Modifier,
    onClickedConfirmButton: (MyPrincipleGroup) -> Unit,
    onClickedAddButton: () -> Unit,
    onClickedClose: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    modalViewModel: PrincipleModalViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val myPrincipleGroups by modalViewModel.principleGroupsState.stateFlow.collectAsStateWithLifecycle()
    val defaultPrincipleGroup by modalViewModel.defaultPrincipleGroupState.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        modalViewModel.getDefaultPrincipleGroup(orderType)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    modalViewModel.getPrincipleGroups(orderType)
                }
                else -> {}
            }
        }

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            modalViewModel.eventFlow
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .collect { event ->
                    when (event) {
                        is Event.ShowErrorToast -> {
                            onShowErrorToast(event.throwable)
                        }
                    }
                }
        }
    }

    PrincipleBottomSheetDialog(
        modifier = modifier,
        title = stringResource(UiR.string.principle_bottom_sheet_dialog_title),
        defaultPrincipleGroup = defaultPrincipleGroup,
        myPrincipleGroups = myPrincipleGroups,
        isShowAddButton = true,
        onClickedClose = onClickedClose,
        onClickedConfirmButton = onClickedConfirmButton,
        onClickedAddButton = onClickedAddButton
    )
}

@Preview(showBackground = true)
@Composable
private fun AiFeedbackScreenPreview() {
    AiFeedbackScreen(
        state = AiFeedbackUiState.Success(
            retrospectionId = 1,
            companyName = "테슬라(TSLA)",
            companyLogo = "",
            price = 100000,
            volume = 100,
            orderType = "매수",
            badge = "platinum",
            principleCheckSummary = PrincipleState(
                keptCount = 1,
                neutralCount = 0,
                notKeptCount = 1
            ),
            keep = listOf(
                "테슬라(TSLA)는 최근 전기차 시장 성장 기대와 혁신 기술에 힘입어 강한 상승세를 보이고 있어요.",
                "테슬라(TSLA)는 최근 전기차 시장 성장 기대와 혁신 기술에 힘입어 강한 상승세를 보이고 있어요."
            ),
            fix = listOf(
                "글로벌 전기차 수요 증가와 친환경 정책 추진으로 전기차 관련 주들이 강세를 나타내고 있어요."
            ),
            next = listOf(
                "전기차 시장 성장 잠재력을 감안하면 장기 투자가 유망해요."
            )
        ),
        grade = HedgeBadge.fromBadge(badge = "platinum"),
        companyLogo = null,
        isCreateMode = true,
        onCompleteClick = {},
        onBack = {},
        companyName = "삼성전자",
        price = 65000,
        stock = 3,
        onClickedPrincipleAddButton = {}
    )
}