package com.depromeet.team5.feature.reasons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeShadow
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.ui.HedgeModal
import com.depromeet.team5.core.ui.component.HedgeLoadingScreen
import com.depromeet.team5.core.ui.model.HedgeBadge
import com.depromeet.team5.core.ui.util.toKoreanYMDOrRaw
import com.depromeet.team5.feature.reasons.model.PrincipleAdherence
import com.depromeet.team5.feature.reasons.model.UiPrinciple
import com.depromeet.team5.feature.reasons.model.UiRetrospection
import com.depromeet.team5.feature.reasons.ui.ImageThumbnailContainer
import com.depromeet.team5.feature.reasons.ui.LinkThumbnailContainer
import com.depromeet.team5.feature.reasons.ui.MemoBottomSheet

@Composable
fun RetrospectionDetailRoute(
    onClickBack: () -> Unit,
    onClickFeedback: (Int) -> Unit,
    onClickImage: (ImageDetail) -> Unit,
    onShowToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RetrospectionDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.stateFlow.collectAsStateWithLifecycle()
    val deleteRetrospectionToastText = stringResource(R.string.delete_retrospection_toast)
    RetrospectionDetailScreen(
        uiState = uiState,
        onClickBack = onClickBack,
        onClickFeedback = onClickFeedback,
        deleteRetrospection = {
            viewModel.deleteRetrospection { throwable ->
                if (throwable != null) {
                    onShowErrorToast(throwable)
                } else {
                    onClickBack()
                    onShowToast(deleteRetrospectionToastText)
                }
            }
        },
        onClickImage = onClickImage,
        onCreateMemo = viewModel::onCreateMemo,
        onUpdateMemo = viewModel::onUpdateMemo,
        onDeleteMemo = viewModel::onRemoveMemo,
        modifier = modifier
    )
}

@Composable
private fun RetrospectionDetailScreen(
    uiState: HedgeUiState<UiRetrospection>,
    onClickBack: () -> Unit,
    onClickFeedback: (Int) -> Unit,
    onClickImage: (ImageDetail) -> Unit,
    onCreateMemo: (String) -> Unit,
    onUpdateMemo: (Int, String) -> Unit,
    onDeleteMemo: (Int) -> Unit,
    deleteRetrospection: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(HedgeColor.Neutral.BackgroundDefault)
            .fillMaxSize()
    ) {
        when (uiState) {
            is HedgeUiState.Success -> {
                RetrospectionDetailScreenContents(
                    retrospection = uiState.data,
                    onClickBack = onClickBack,
                    onClickFeedback = onClickFeedback,
                    onClickDeleteRetrospection = deleteRetrospection,
                    onClickImage = onClickImage,
                    onClickCreateMemo = onCreateMemo,
                    onClickUpdateMemo = onUpdateMemo,
                    onClickDeleteMemo = onDeleteMemo,
                )
            }
            is HedgeUiState.Loading -> {
                HedgeLoadingScreen()
            }
            is HedgeUiState.Error -> {
                //todo error
            }
        }
    }
}

@Composable
private fun BoxScope.RetrospectionDetailScreenContents(
    retrospection: UiRetrospection,
    onClickBack: () -> Unit,
    onClickFeedback: (Int) -> Unit,
    onClickDeleteRetrospection: () -> Unit,
    onClickCreateMemo: (String) -> Unit,
    onClickUpdateMemo: (Int, String) -> Unit,
    onClickDeleteMemo: (Int) -> Unit,
    onClickImage: (ImageDetail) -> Unit,
    modifier: Modifier = Modifier,
) {

    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(initialPage = 0) { retrospection.principleGroupState.principles.size }
    var showDeleteRetrospectionModal by remember { mutableStateOf(false) }
    var showMemoBottomSheet by remember { mutableStateOf(false) }
    var topBarBottom by remember { mutableFloatStateOf(0f) }
    var principleTitleOffsetY by remember { mutableFloatStateOf(0f) }
    val isTitleHidden by remember {
        derivedStateOf { principleTitleOffsetY <= topBarBottom && pagerState.isScrollInProgress.not() }
    }

    HedgeModal(
        showModal = showDeleteRetrospectionModal,
        title = stringResource(R.string.delete_retrospection_title),
        description = stringResource(R.string.delete_retrospection_description),
        submitButton = stringResource(R.string.delete) to {
            onClickDeleteRetrospection()
            showDeleteRetrospectionModal = false
        },
        cancelButton = stringResource(R.string.cancel) to {
            showDeleteRetrospectionModal = false
        },
        onDismissRequest = { showDeleteRetrospectionModal = false }
    )

    MemoBottomSheet(
        showBottomSheet = showMemoBottomSheet,
        memos = retrospection.memos,
        onClickCreateMemo = onClickCreateMemo,
        onClickUpdateMemo = onClickUpdateMemo,
        onClickDeleteMemo = onClickDeleteMemo,
        onClickClose = { showMemoBottomSheet = false },
        onDismissRequest = { showMemoBottomSheet = false },
    )

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        HedgeTopBar(
            onClickBack = onClickBack,
            action = {
                HedgeButton.Text(
                    text = stringResource(id = R.string.delete),
                    forceClickable = true,
                    imageVector = null,
                    color = HedgeButton.Text.Color.Secondary,
                    onClick = { showDeleteRetrospectionModal = true },
                )
            },
            modifier = Modifier.onGloballyPositioned { topBarBottom = it.boundsInRoot().bottom }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            TradeInfo(
                thumbnail = retrospection.companyLogo,
                companyName = retrospection.companyName,
                price = retrospection.price,
                currency = retrospection.currency,
                volume = retrospection.volume,
                orderDate = retrospection.orderDate,
                orderType = retrospection.orderType,
                returnRate = retrospection.returnRate,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            )
            Spacer(Modifier.size(4.dp))
            AiFeedback(
                badge = retrospection.badge,
                onClickFeedback = { onClickFeedback(retrospection.id) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.size(28.dp))
            PrincipleGroupTitle(
                currentPage = pagerState.currentPage + 1,
                pageCount = pagerState.pageCount,
                groupName = retrospection.principleGroupState.groupName,
                memosSize = retrospection.memos.size,
                onClickMemo = { showMemoBottomSheet = true },
                modifier = Modifier.padding(start = 20.dp, end = 16.dp),
            )
            //todo paging scroll 영역 화면 하단까지 늘리기..
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->
                val principle = retrospection.principleGroupState.principles[page]
                RetrospectionPage(
                    principleState = principle,
                    onClickImage = { onClickImage(ImageDetail(principle.principleChecks.imageUrls, it)) },
                    onTitlePositioned = { principleTitleOffsetY = it },
                    modifier = Modifier
                )
            }
        }
    }

    FloatingPrincipleTitle(
        isTitleHidden = isTitleHidden,
        adherence = retrospection.principleGroupState.principles[pagerState.currentPage].principleChecks.adherence,
        principleTitle = retrospection.principleGroupState.principles[pagerState.currentPage].principle,
        memosSize = retrospection.memos.size,
        onClickMemo = { showMemoBottomSheet = true },
        modifier = Modifier
            .padding(top = 44.dp)
            .background(HedgeColor.Neutral.BackgroundDefault)
            .align(Alignment.TopCenter)
    )
    if (retrospection.principleGroupState.principles.size >= 2) {
        PageIndicatorContainer(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun TradeInfo(
    thumbnail: String?,
    companyName: String,
    price: Int,
    currency: String,
    volume: Int,
    orderDate: String,
    orderType: OrderType,
    returnRate: Double?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            thumbnail?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                )
            } ?: Icon(
                imageVector = HedgeIcon.COMPANY_LOGO,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(22.dp),
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = companyName,
                color = HedgeColor.Text.Title,
                style = HedgeTypography.Body3.Medium,
            )
            returnRate?.toInt()?.let {
                Spacer(Modifier.size(4.dp))
                Text(
                    text = "${it}%",
                    color = if (it < 0) HedgeColor.Trade.Sell else if (it == 0) HedgeColor.Text.Primary else HedgeColor.Trade.Buy,
                    style = HedgeTypography.Body3.SemiBold,
                )
            }
        }
        Spacer(Modifier.size(4.dp))
        Text(
            text = stringResource(
                R.string.trade_info,
                price,
                currency,
                volume,
                stringResource(if (orderType == OrderType.BUY) R.string.buy else R.string.sell)
            ),
            color = HedgeColor.Text.Title,
            style = HedgeTypography.Headline1.SemiBold,
        )
        Spacer(Modifier.size(4.dp))
        Text(
            text = orderDate.toKoreanYMDOrRaw(),
            color = HedgeColor.Text.Alternative,
            style = HedgeTypography.Label2.Regular,
        )
    }
}

@Composable
private fun AiFeedback(
    badge: HedgeBadge,
    onClickFeedback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(color = HedgeColor.Neutral.BackgroundSecondary)
            .clickable(
                onClick = onClickFeedback
            )
            .padding(start = 14.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(badge.thumbnailRes),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(25.dp)
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = stringResource(R.string.go_ai_feedback),
            color = HedgeColor.Text.Secondary,
            style = HedgeTypography.Label1.Medium,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = HedgeIcon.ArrowRightThin,
            contentDescription = null,
            tint = HedgeColor.Text.Assistive,
            modifier = Modifier
                .size(20.dp)
        )
    }
}

@Composable
private fun PrincipleGroupTitle(
    currentPage: Int,
    pageCount: Int,
    groupName: String,
    memosSize: Int,
    onClickMemo: () -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$currentPage",
            color = HedgeColor.Brand.Darken,
            style = HedgeTypography.Label1.SemiBold,
        )
        Text(
            text = "/$pageCount",
            color = HedgeColor.Text.Assistive,
            style = HedgeTypography.Label1.Medium,
        )
        Spacer(Modifier.size(6.dp))
        VerticalDivider(
            color = HedgeColor.Text.Disabled,
            modifier = Modifier.height(12.dp)
        )
        Spacer(Modifier.size(6.dp))
        Text(
            text = groupName,
            color = HedgeColor.Text.Assistive,
            style = HedgeTypography.Label2.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClickMemo() }
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_memo),
                contentDescription = null,
                tint = HedgeColor.Text.Assistive,
            )
            Text(
                text = if (memosSize == 0) stringResource(R.string.memo_add) else "$memosSize",
                color = HedgeColor.Text.Alternative,
                style = HedgeTypography.Label1.Medium
            )
        }
    }
}

@Composable
private fun RetrospectionPage(
    principleState: UiPrinciple,
    onClickImage: (Int) -> Unit,
    onTitlePositioned: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = principleState.principle,
            color = HedgeColor.Text.Title,
            style = HedgeTypography.Body1.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .onGloballyPositioned { onTitlePositioned(it.boundsInRoot().top) }
        )
        Spacer(Modifier.size(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            val adherence = principleState.principleChecks.adherence
            Icon(
                modifier = Modifier
                    .size(18.dp),
                painter = when (adherence) {
                    PrincipleAdherence.KEPT -> painterResource(R.drawable.ic_circle)
                    PrincipleAdherence.NEUTRAL -> painterResource(R.drawable.ic_triangle)
                    PrincipleAdherence.NOT_KEPT -> painterResource(R.drawable.ic_cross)
                    else -> error("UNSELECTED should never reach here")
                },
                tint = HedgeColor.Brand.Primary,
                contentDescription = null,
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = when (adherence) {
                    PrincipleAdherence.KEPT -> stringResource(R.string.principle_followed)
                    PrincipleAdherence.NEUTRAL -> stringResource(R.string.principle_neutral)
                    PrincipleAdherence.NOT_KEPT -> stringResource(R.string.principle_not_followed)
                    else -> stringResource(R.string.selecte_before)
                },
                style = HedgeTypography.Body3.SemiBold,
                color = HedgeColor.Brand.Darken,
            )
        }
        Spacer(Modifier.size(28.dp))
        val note = principleState.principleChecks.note.text
        Text(
            text = if (note.isEmpty() || note.isBlank()) stringResource(R.string.empty_retrospection) else note,
            color = if (note.isEmpty() || note.isBlank()) HedgeColor.Text.Assistive else HedgeColor.Text.Primary,
            style = HedgeTypography.Body3.Regular,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(Modifier.size(8.dp))
        ImageThumbnailContainer(
            images = principleState.principleChecks.imageUrls.map { it.toUri() },
            onClickImage = onClickImage,
            modifier = Modifier
                .padding(
                    top = if (principleState.principleChecks.imageUrls.isNotEmpty()) 16.dp else 0.dp,
                )
        )
        LinkThumbnailContainer(
            articles = principleState.principleChecks.articles,
            spacedBy = 16.dp,
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = if (principleState.principleChecks.articles.isNotEmpty()) 16.dp else 0.dp,
                ),
        )
        Spacer(Modifier.size(24.dp))
    }
}

@Composable
private fun FloatingPrincipleTitle(
    isTitleHidden: Boolean,
    adherence: PrincipleAdherence,
    principleTitle: String,
    memosSize: Int,
    onClickMemo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isTitleHidden,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = modifier
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .size(15.dp),
                    painter = when (adherence) {
                        PrincipleAdherence.KEPT -> painterResource(R.drawable.ic_circle)
                        PrincipleAdherence.NEUTRAL -> painterResource(R.drawable.ic_triangle)
                        PrincipleAdherence.NOT_KEPT -> painterResource(R.drawable.ic_cross)
                        else -> error("UNSELECTED should never reach here")
                    },
                    tint = HedgeColor.Brand.Primary,
                    contentDescription = null,
                )
                Spacer(Modifier.size(12.dp))
                Text(
                    text = principleTitle,
                    color = HedgeColor.Text.Title,
                    style = HedgeTypography.Body3.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onClickMemo() }
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_memo),
                        contentDescription = null,
                        tint = HedgeColor.Text.Assistive,
                    )
                    Text(
                        text = if (memosSize == 0) stringResource(R.string.memo_add) else "$memosSize",
                        color = HedgeColor.Text.Alternative,
                        style = HedgeTypography.Label1.Medium
                    )
                }
            }
            HorizontalDivider(color = HedgeColor.GREY_200)
        }
    }
}

@Composable
private fun PageIndicatorContainer(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    0f to Color.Transparent,
                    0.2356f to HedgeColor.Neutral.BackgroundDefault.copy(0.2f),
                    1f to HedgeColor.Neutral.BackgroundDefault.copy(0.98f)
                ),
            )
            .padding(bottom = 32.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        PageIndicator(
            pageSize = pagerState.pageCount,
            currentPage = pagerState.currentPage,
        )
    }
}

@Composable
private fun PageIndicator(
    pageSize: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(29.dp)
    Row(
        modifier = modifier
            .dropShadow(
                shape = shape,
                shadow = HedgeShadow.Regular,
            )
            .background(
                color = HedgeColor.Text.Alternative,
                shape = shape,
            )
            .padding(horizontal = 20.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pageSize) { idx ->
            val color = if (currentPage == idx) Color(0xFF09D990) else HedgeColor.Text.Disabled
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp)
            )
        }
    }
}

@Composable
@Preview
private fun RetrospectionDetailScreenPreview() {
    RetrospectionDetailScreen(
        uiState = HedgeUiState.Success(previewRetrospection),
        onClickBack = {},
        onClickFeedback = {},
        deleteRetrospection = {},
        onClickImage = {},
        onCreateMemo = {},
        onUpdateMemo = { _, _ -> },
        onDeleteMemo = { },
    )
}