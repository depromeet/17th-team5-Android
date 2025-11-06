package com.depromeet.team5.feature.reasons

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeToast
import com.depromeet.team5.core.designsystem.component.HedgeToastState
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.core.ui.HedgeModal
import com.depromeet.team5.feature.reasons.model.PrincipleAdherence
import com.depromeet.team5.feature.reasons.model.RestrictionAttachment
import com.depromeet.team5.feature.reasons.model.TradeInfo
import com.depromeet.team5.feature.reasons.model.UiPrinciple
import com.depromeet.team5.feature.reasons.model.UiPrincipleGroup
import com.depromeet.team5.feature.reasons.model.toUi
import com.depromeet.team5.feature.reasons.ui.AutoScrollTextField
import com.depromeet.team5.feature.reasons.ui.ImageThumbnailContainer
import com.depromeet.team5.feature.reasons.ui.InputToolBar
import com.depromeet.team5.feature.reasons.ui.InputToolBarIme
import com.depromeet.team5.feature.reasons.ui.LinkModal
import com.depromeet.team5.feature.reasons.ui.LinkThumbnailContainer
import com.depromeet.team5.feature.reasons.ui.PrincipleAdherenceContainer
import com.depromeet.team5.feature.reasons.ui.RestrictionIndicatorContainer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun ReasonRoute(
    onClickBack: () -> Unit,
    onClickDone: () -> Unit,
    onClickImage: (ImageDetail) -> Unit,
    modifier: Modifier = Modifier,
    requestViewModel: RequestViewModel,
    viewModel: ReasonViewModel = hiltViewModel(),
) {
    val uiState by viewModel.hedgeUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (uiState is HedgeUiState.Success) return@LaunchedEffect
        requestViewModel.selectedMyPrincipleGroup?.let { myPrincipleGroup ->
            viewModel.initPrincipleGroup(myPrincipleGroup.toUi())
        }
        viewModel.initTradeInfo(
            TradeInfo(
                stockName = requestViewModel.request.companyName,
                orderType = requestViewModel.request.orderType,
                price = requestViewModel.request.price.toLong(),
                currency = requestViewModel.request.currency,
                volume = requestViewModel.request.volume,
                orderDate = requestViewModel.request.orderDate
            )
        )
    }

    when (val state = uiState) {
        is HedgeUiState.Success -> {
            ReasonsScreen(
                initialPrincipleGroup = viewModel.initialPrincipleGroup,
                principleGroup = state.data.second,
                tradeInfo = state.data.first,
                onClickBack = onClickBack,
                onClickDone = {
                    requestViewModel.selectedMyPrincipleGroup = state.data.second.toDomain()
                    onClickDone()
                },
                onClickImage = onClickImage,
                onAddImages = { idx, uris -> viewModel.onAddImages(idx, uris) },
                onAddArticle = { idx, link -> viewModel.onAddArticle(idx, link) },
                onClickDeleteImage = { idx, index -> viewModel.onRemoveImage(idx, index) },
                onClickDeleteLink = { idx, index -> viewModel.onRemoveArticle(idx, index) },
                onAdherenceChanged = { idx, value -> viewModel.onAdherenceChanged(idx, value) },
                onReasonChanged = { idx, value -> viewModel.onNoteChanged(idx, value) },
                modifier = modifier,
            )
        }
        else -> {}
    }
}

@Composable
private fun ReasonsScreen(
    initialPrincipleGroup: UiPrincipleGroup?,
    principleGroup: UiPrincipleGroup,
    tradeInfo: TradeInfo,
    onClickBack: () -> Unit,
    onClickDone: () -> Unit,
    onClickImage: (ImageDetail) -> Unit,
    onAddImages: (Int, List<String>) -> Unit,
    onAddArticle: (Int, String) -> Unit,
    onClickDeleteImage: (Int, Int) -> Unit,
    onClickDeleteLink: (Int, Int) -> Unit,
    onAdherenceChanged: (Int, PrincipleAdherence) -> Unit,
    onReasonChanged: (Int, TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val hedgeToastState = remember { HedgeToastState() }
    var limitedAttachmentType: RestrictionAttachment? by remember { mutableStateOf(null) }
    var showCompleteModal by remember { mutableStateOf(false) }
    var showBackModal by remember { mutableStateOf(false) }
    var showAddLinkModal by remember { mutableStateOf(false) }
    var showAddMentionModal by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(initialPage = 0) { principleGroup.principles.size }

    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
            if (uris.isNotEmpty()) {
                val remain =
                    ReasonViewModel.PRINCIPLE_ATTACHMENT_LIMIT - principleGroup.principles[pagerState.currentPage].principleChecks.imageUrls.size

                val addCount = remain.coerceAtMost(uris.size)
                if (addCount > 0) {
                    onAddImages(pagerState.currentPage, uris.take(addCount).map { it.toString() })
                }

                if (uris.size > remain) {
                    limitedAttachmentType = RestrictionAttachment.IMAGE
                }
            }
        }

    BackHandler {
        if (initialPrincipleGroup == principleGroup) onClickBack()
        else showBackModal = true
    }

    HedgeToast(hedgeToastState)

    HedgeModal(
        showModal = limitedAttachmentType != null,
        icon = null,
        title = stringResource(if (limitedAttachmentType == RestrictionAttachment.IMAGE) R.string.limited_image_modal_title else R.string.limited_link_modal_title),
        description = stringResource(if (limitedAttachmentType == RestrictionAttachment.IMAGE) R.string.limited_image_modal_description else R.string.limited_link_modal_description),
        submitButton = stringResource(R.string.submit) to { limitedAttachmentType = null },
        cancelButton = null,
        onDismissRequest = { limitedAttachmentType = null }
    )

    HedgeModal(
        showModal = showCompleteModal,
        icon = null,
        title = stringResource(R.string.complete_modal_title),
        description = stringResource(R.string.complete_modal_description),
        submitButton = stringResource(R.string.complete) to {
            showCompleteModal = false
            onClickDone()
        },
        cancelButton = stringResource(R.string.cancel) to {
            showCompleteModal = false
        },
        onDismissRequest = {
            showCompleteModal = false
        }
    )

    HedgeModal(
        showModal = showBackModal,
        title = stringResource(R.string.back_modal_title),
        description = stringResource(R.string.back_modal_description),
        submitButton = stringResource(R.string.go_back) to {
            showBackModal = false
            onClickBack()
        },
        cancelButton = stringResource(R.string.cancel) to {
            showBackModal = false
        },
        onDismissRequest = {
            showBackModal = false
        },
    )

    HedgeModal(
        showModal = showAddMentionModal,
        icon = null,
        title = stringResource(id = R.string.mention_modal_title),
        description = stringResource(id = R.string.mention_modal_description),
        submitButton = stringResource(R.string.excited) to {
            showAddMentionModal = false
        },
        cancelButton = stringResource(R.string.not_needed) to {
            showAddMentionModal = false
        },
        onDismissRequest = {
            showAddMentionModal = false
        },
    )

    LinkModal(
        showDialog = showAddLinkModal,
        onClickSubmit = { link ->
            onAddArticle(pagerState.currentPage, link)
            showAddLinkModal = false
        },
        onClickCancel = {
            showAddLinkModal = false
        },
        onDismissRequest = {
            showAddLinkModal = false
        },
    )
    ReasonScreenContents(
        principleGroup = principleGroup,
        tradeInfo = tradeInfo,
        pagerState = pagerState,
        onClickBack = {
            if (initialPrincipleGroup == principleGroup) onClickBack()
            else showBackModal = true
        },
        onClickDone = {
            if (principleGroup.isAllPrincipleChecked()) showCompleteModal = true
            else {
                coroutineScope.launch {
                    hedgeToastState.show(context.getString(R.string.cannot_complete_restriction))
                    pagerState.animateScrollToPage(principleGroup.getIndexOfFirstUnselectedPrinciple())
                }
            }
        },
        onClickAddImage = { pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)) },
        onClickAddLink = {
            if (principleGroup.principles[pagerState.currentPage].principleChecks.articles.size >= 3) limitedAttachmentType =
                RestrictionAttachment.LINK
            else showAddLinkModal = true
        },
        onClickAddMention = { showAddMentionModal = true },
        onClickImage = { onClickImage(ImageDetail(principleGroup.principles[pagerState.currentPage].principleChecks.imageUrls, it)) },
        onClickDeleteImage = { onClickDeleteImage(pagerState.currentPage, it) },
        onClickDeleteLink = { onClickDeleteLink(pagerState.currentPage, it) },
        onReasonChanged = onReasonChanged,
        onAdherenceChanged = { onAdherenceChanged(pagerState.currentPage, it) },
        modifier = modifier
    )
}

@Composable
private fun ReasonScreenContents(
    principleGroup: UiPrincipleGroup,
    tradeInfo: TradeInfo,
    pagerState: PagerState,
    onClickBack: () -> Unit,
    onClickDone: () -> Unit,
    onClickAddImage: () -> Unit,
    onClickAddLink: () -> Unit,
    onClickAddMention: () -> Unit,
    onClickImage: (Int) -> Unit,
    onClickDeleteImage: (Int) -> Unit,
    onClickDeleteLink: (Int) -> Unit,
    onAdherenceChanged: (PrincipleAdherence) -> Unit,
    onReasonChanged: (Int, TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
    ) {
        val density = LocalDensity.current
        val isImeVisible = WindowInsets.ime.getBottom(density) > 0
        val focusRequesters = remember(principleGroup.principles.size) {
            List(principleGroup.principles.size) { FocusRequester() }
        }
        LaunchedEffect(pagerState, isImeVisible) {
            snapshotFlow { pagerState.currentPage }
                .distinctUntilChanged()
                .collect { page ->
                    if (isImeVisible) {
                        focusRequesters[page].requestFocus()
                    }
                }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            HedgeTopBar(
                onClickBack = onClickBack,
                title = {
                    Text(
                        text = principleGroup.groupName,
                        color = HedgeColor.Text.Primary,
                        style = HedgeTypography.Body3.SemiBold
                    )
                },
                action = {
                    HedgeButton.Text(
                        text = stringResource(id = R.string.done),
                        enabled = principleGroup.isAllPrincipleChecked(),
                        forceClickable = true,
                        imageVector = null,
                        onClick = onClickDone,
                    )
                }
            )
            if (isImeVisible.not()) {
                TradeInfo(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    tradeInfo = tradeInfo,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = HedgeColor.Neutral.BackgroundSecondary
                )
            }
            HorizontalPager(
                state = pagerState
            ) { page ->
                ReasonsPage(
                    principle = principleGroup.principles[page],
                    isImeVisible = isImeVisible,
                    focusRequester = focusRequesters[page],
                    onClickAddImage = onClickAddImage,
                    onClickAddLink = onClickAddLink,
                    onClickAddMention = onClickAddMention,
                    onClickImage = onClickImage,
                    onClickDeleteImage = onClickDeleteImage,
                    onClickDeleteLink = onClickDeleteLink,
                    onAdherenceChanged = onAdherenceChanged,
                    onReasonChanged = { onReasonChanged(page, it) },
                )
            }
        }
        RestrictionIndicatorContainer(
            checkedAdherenceCount = principleGroup.getCheckedPrincipleCount(),
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun TradeInfo(
    tradeInfo: TradeInfo,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tradeInfo.logoUri?.let {
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
        Spacer(Modifier.size(12.dp))
        Text(
            text = tradeInfo.stockName,
            color = HedgeColor.Text.Alternative,
            style = HedgeTypography.Label1.Medium,
        )
        Spacer(Modifier.size(2.dp))
        Text(
            text = stringResource(
                R.string.trade_info,
                tradeInfo.price,
                tradeInfo.currency,
                tradeInfo.volume,
                stringResource(if (tradeInfo.orderType == OrderType.BUY) R.string.buy else R.string.sell)
            ),
            color = HedgeColor.Trade.Sell,
            style = HedgeTypography.Label1.SemiBold,
        )
    }
}

@Composable
private fun ReasonsPage(
    principle: UiPrinciple,
    isImeVisible: Boolean,
    focusRequester: FocusRequester,
    onClickAddImage: () -> Unit,
    onClickAddLink: () -> Unit,
    onClickAddMention: () -> Unit,
    onClickImage: (Int) -> Unit,
    onClickDeleteImage: (Int) -> Unit,
    onClickDeleteLink: (Int) -> Unit,
    onAdherenceChanged: (PrincipleAdherence) -> Unit,
    onReasonChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        if (isImeVisible.not()) {
            ExpandedPrincipleHeader(
                title = principle.principle,
                description = principle.description,
                adherence = principle.principleChecks.adherence,
                onAdherenceChanged = onAdherenceChanged,
            )
        } else {
            CompactPrincipleHeader(
                title = principle.principle,
                adherence = principle.principleChecks.adherence,
            )
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(vertical = 24.dp),
        ) {
            AutoScrollTextField(
                content = principle.principleChecks.note,
                onValueChange = onReasonChanged,
                isImeVisible = isImeVisible,
                focusRequester = focusRequester,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 8.dp)
            )
            ImageThumbnailContainer(
                images = principle.principleChecks.imageUrls.map { it.toUri() },
                onClickDeleteImage = onClickDeleteImage,
                onClickImage = onClickImage,
            )
            LinkThumbnailContainer(
                articles = principle.principleChecks.articles,
                onClickDeleteLink = onClickDeleteLink,
            )
            if (isImeVisible.not()) {
                InputToolBar(
                    hasImages = principle.principleChecks.imageUrls.isNotEmpty(),
                    hasLinks = principle.principleChecks.articles.isNotEmpty(),
                    onClickAddImage = onClickAddImage,
                    onClickAddLink = onClickAddLink,
                    onClickAddMention = onClickAddMention,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
            }
        }
        if (isImeVisible) {
            InputToolBarIme(
                modifier = Modifier
                    .imePadding(),
                hasImages = principle.principleChecks.imageUrls.isNotEmpty(),
                hasLinks = principle.principleChecks.articles.isNotEmpty(),
                onClickAddImage = onClickAddImage,
                onClickAddLink = onClickAddLink,
                onClickAddMention = onClickAddMention,
            )
        }
    }
}


@Composable
private fun ExpandedPrincipleHeader(
    title: String,
    description: String,
    adherence: PrincipleAdherence,
    onAdherenceChanged: (PrincipleAdherence) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .clickable(
                onClick = { isExpanded = !isExpanded },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.ask_adherence),
            color = HedgeColor.Text.Title,
            style = HedgeTypography.Body3.Medium,
        )
        Spacer(Modifier.size(4.dp))
        Row(
            modifier
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = title,
                color = HedgeColor.Text.Title,
                style = HedgeTypography.Headline1.SemiBold,
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.size(12.dp))
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                imageVector = if (isExpanded) HedgeIcon.ArrowUp else HedgeIcon.ArrowDown,
                contentDescription = "expand",
            )
        }
        if (isExpanded) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = description,
                style = HedgeTypography.Body3.Medium,
                color = HedgeColor.Text.Alternative,
            )
        }
    }
    Spacer(Modifier.size(24.dp))
    PrincipleAdherenceContainer(
        checkedPrincipleAdherence = adherence,
        modifier = Modifier
            .padding(horizontal = 20.dp),
        onAdherenceChanged = onAdherenceChanged
    )
    Spacer(Modifier.size(8.dp))
}

@Composable
private fun CompactPrincipleHeader(
    title: String,
    adherence: PrincipleAdherence,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, top = 28.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title,
            color = HedgeColor.Text.Title,
            style = HedgeTypography.Body2.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(Modifier.size(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (adherence == PrincipleAdherence.UNSELECTED) {
                Icon(
                    modifier = Modifier
                        .size(18.dp),
                    painter = painterResource(R.drawable.ic_circle),
                    tint = HedgeColor.Brand.Disabled,
                    contentDescription = null,
                )
                Spacer(Modifier.size(3.dp))
                Icon(
                    modifier = Modifier
                        .size(18.dp),
                    painter = painterResource(R.drawable.ic_triangle),
                    tint = HedgeColor.Brand.Disabled,
                    contentDescription = null,
                )
                Spacer(Modifier.size(3.dp))
                Icon(
                    modifier = Modifier
                        .size(18.dp),
                    painter = painterResource(R.drawable.ic_cross),
                    tint = HedgeColor.Brand.Disabled,
                    contentDescription = null,
                )
            } else {
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
            }
            Spacer(Modifier.size(4.dp))
            Text(
                text = when (adherence) {
                    PrincipleAdherence.KEPT -> stringResource(R.string.principle_followed)
                    PrincipleAdherence.NEUTRAL -> stringResource(R.string.principle_neutral)
                    PrincipleAdherence.NOT_KEPT -> stringResource(R.string.principle_not_followed)
                    else -> stringResource(R.string.selecte_before)
                },
                style = if (adherence == PrincipleAdherence.UNSELECTED) HedgeTypography.Body3.Medium else HedgeTypography.Body3.SemiBold,
                color = if (adherence == PrincipleAdherence.UNSELECTED) HedgeColor.Text.Assistive else HedgeColor.Brand.Darken,
            )
        }
        Spacer(Modifier.size(12.dp))
        HorizontalDivider(
            color = HedgeColor.Neutral.BackgroundSecondary
        )
    }
}

@Composable
@Preview
private fun ReasonScreenPreview() {
    val principleGroup by remember { mutableStateOf(previewUiPrincipleGroup) }
    val tradeInfo by remember { mutableStateOf(previewTradeInfo) }
    var reason by remember { mutableStateOf(TextFieldValue("")) }
    ReasonsScreen(
        initialPrincipleGroup = principleGroup,
        principleGroup = principleGroup,
        tradeInfo = tradeInfo,
        onClickBack = {},
        onClickDone = {},
        onClickImage = {},
        onAddImages = { idx, uris -> },
        onAddArticle = { idx, link -> },
        onClickDeleteImage = { idx, index -> },
        onClickDeleteLink = { idx, index -> },
        onAdherenceChanged = { idx, value -> },
        onReasonChanged = { idx, value -> reason = value },
    )
}