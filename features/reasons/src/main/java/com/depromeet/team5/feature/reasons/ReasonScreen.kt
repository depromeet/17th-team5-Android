package com.depromeet.team5.feature.reasons

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeToast
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.navigation.request.OrderTypeParams
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.core.ui.HedgeModal
import com.depromeet.team5.feature.reasons.ui.AutoScrollTextField
import com.depromeet.team5.feature.reasons.ui.ImageThumbnailContainer
import com.depromeet.team5.feature.reasons.ui.InputToolBar
import com.depromeet.team5.feature.reasons.ui.InputToolBarIme
import com.depromeet.team5.feature.reasons.ui.LinkModal
import com.depromeet.team5.feature.reasons.ui.LinkThumbnailContainer
import com.depromeet.team5.feature.reasons.ui.PrincipleAdherenceContainer
import com.depromeet.team5.feature.reasons.ui.RestrictionIndicatorContainer
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
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val principleTemplate by viewModel.principleTemplate.stateFlow.collectAsStateWithLifecycle()
    val tradeInfo by viewModel.tradeInfo.stateFlow.collectAsStateWithLifecycle()
    var toastMessage: String? by remember { mutableStateOf(null) }
    var limitedAttachmentType: RestrictionAttachment? by remember { mutableStateOf(null) }
    var showCompleteModal by remember { mutableStateOf(false) }
    var showBackModal by remember { mutableStateOf(false) }
    var showAddLinkModal by remember { mutableStateOf(false) }
    var showAddMentionModal by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(initialPage = 0) { principleTemplate.principles.size }

    val pickMultipleMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
            if (uris.isNotEmpty()) {
                val remainCount =
                    ReasonViewModel.PRINCIPLE_ATTACHMENT_LIMIT - principleTemplate.principles[pagerState.currentPage].images.size
                if (remainCount == ReasonViewModel.PRINCIPLE_ATTACHMENT_LIMIT) {
                    viewModel.onAddImages(pagerState.currentPage, uris.map { it.toString() })
                } else {
                    if (remainCount > 0) {
                        viewModel.onAddImages(
                            pagerState.currentPage,
                            uris.subList(0, remainCount).map { it.toString() }
                        )
                    }
                    if (remainCount < uris.size) limitedAttachmentType = RestrictionAttachment.IMAGE
                }
            }
        }

    BackHandler {
        if (viewModel.initialPrincipleTemplate == principleTemplate) onClickBack()
        else showBackModal = true
    }

    LaunchedEffect(Unit) {
        viewModel.initTradeInfo(
            TradeInfo(
                logoDrawableRes = R.drawable.ic_company_logo,
                stockName = requestViewModel.request.companyName,
                orderType = requestViewModel.request.orderType,
                price = requestViewModel.request.price.toLong(),
                currency = requestViewModel.request.currency,
                volume = requestViewModel.request.volume,
                orderDate = requestViewModel.request.orderDate
            )
        )
    }

    toastMessage?.let { HedgeToast(it) { toastMessage = null } }

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
            viewModel.onAddArticle(pagerState.currentPage, link)
            showAddLinkModal = false
        },
        onClickCancel = {
            showAddLinkModal = false
        },
        onDismissRequest = {
            showAddLinkModal = false
        },
    )

    ReasonsScreen(
        principleTemplate = principleTemplate,
        tradeInfo = tradeInfo,
        pagerState = pagerState,
        onClickBack = {
            if (viewModel.initialPrincipleTemplate == principleTemplate) onClickBack()
            else showBackModal = true
        },
        onClickDone = {
            if (principleTemplate.isAllPrincipleChecked()) showCompleteModal = true
            else {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(principleTemplate.getIndexOfFirstUnselectedPrinciple())
                }
                toastMessage = context.getString(R.string.cannot_complete_restriction)
            }
        },
        onClickAddImage = { pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)) },
        onClickAddLink = {
            if (principleTemplate.principles[pagerState.currentPage].articles.size >= 3) limitedAttachmentType = RestrictionAttachment.LINK
            else showAddLinkModal = true
        },
        onClickAddMention = { showAddMentionModal = true },
        onClickImage = { onClickImage(ImageDetail(pagerState.currentPage, it)) },
        onClickDeleteImage = { viewModel.onRemoveImage(pagerState.currentPage, it) },
        onClickDeleteLink = { viewModel.onRemoveArticle(pagerState.currentPage, it) },
        onReasonChanged = { viewModel.onNoteChanged(pagerState.currentPage, it) },
        onAdherenceChanged = { viewModel.onAdherenceChanged(pagerState.currentPage, it) },
        modifier = modifier
    )
}

@Composable
private fun ReasonsScreen(
    principleTemplate: PrincipleTemplate,
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
    onReasonChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
    ) {
        val density = LocalDensity.current
        val isImeVisible = WindowInsets.ime.getBottom(density) > 0

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            HedgeTopBar(
                onClickBack = onClickBack,
                title = {
                    Text(
                        text = principleTemplate.name,
                        color = HedgeColor.Text.Primary,
                        style = HedgeTypography.Body3.SemiBold
                    )
                },
                action = {
                    HedgeButton.Text(
                        text = stringResource(id = R.string.done),
                        enabled = principleTemplate.isAllPrincipleChecked(),
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
                    principle = principleTemplate.principles[page],
                    isImeVisible = isImeVisible,
                    onClickAddImage = onClickAddImage,
                    onClickAddLink = onClickAddLink,
                    onClickAddMention = onClickAddMention,
                    onClickImage = onClickImage,
                    onClickDeleteImage = onClickDeleteImage,
                    onClickDeleteLink = onClickDeleteLink,
                    onAdherenceChanged = onAdherenceChanged,
                    onReasonChanged = onReasonChanged,
                )
            }
        }
        RestrictionIndicatorContainer(
            checkedAdherenceCount = principleTemplate.getCheckedPrincipleCount(),
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
        Icon(
            painter = painterResource(id = tradeInfo.logoDrawableRes),
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
                stringResource(if (tradeInfo.orderType == OrderTypeParams.BUY) R.string.buy else R.string.sell)
            ),
            color = HedgeColor.Trade.Sell,
            style = HedgeTypography.Label1.SemiBold,
        )
    }
}

@Composable
private fun ReasonsPage(
    principle: Principle,
    isImeVisible: Boolean,
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
                title = principle.title,
                description = principle.description,
                adherence = principle.adherence,
                onAdherenceChanged = onAdherenceChanged,
            )
        } else {
            CompactPrincipleHeader(
                title = principle.title,
                adherence = principle.adherence,
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
                content = principle.note,
                onValueChange = onReasonChanged,
                isImeVisible = isImeVisible,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 8.dp)
            )
            ImageThumbnailContainer(
                images = principle.images.map { it.toUri() },
                onClickDeleteImage = onClickDeleteImage,
                onClickImage = onClickImage,
            )
            LinkThumbnailContainer(
                articles = principle.articles,
                onClickDeleteLink = onClickDeleteLink,
            )
            if (isImeVisible.not()) {
                InputToolBar(
                    hasImages = principle.images.isNotEmpty(),
                    hasLinks = principle.articles.isNotEmpty(),
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
                hasImages = principle.images.isNotEmpty(),
                hasLinks = principle.articles.isNotEmpty(),
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
                        PrincipleAdherence.KEEP -> painterResource(R.drawable.ic_circle)
                        PrincipleAdherence.NEUTRAL -> painterResource(R.drawable.ic_triangle)
                        PrincipleAdherence.BREAK -> painterResource(R.drawable.ic_cross)
                        else -> error("UNSELECTED should never reach here")
                    },
                    tint = HedgeColor.Brand.Primary,
                    contentDescription = null,
                )
            }
            Spacer(Modifier.size(4.dp))
            Text(
                text = when (adherence) {
                    PrincipleAdherence.KEEP -> stringResource(R.string.principle_followed)
                    PrincipleAdherence.NEUTRAL -> stringResource(R.string.principle_neutral)
                    PrincipleAdherence.BREAK -> stringResource(R.string.principle_not_followed)
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
    val principleTemplate by remember { mutableStateOf(PrincipleTemplate.RETROSPECT_ENTRY) }
    val tradeInfo by remember { mutableStateOf(ReasonViewModel.dummyTradeInfo) }
    var reason by remember { mutableStateOf(TextFieldValue("")) }
    val pagerState = rememberPagerState(initialPage = 0) { principleTemplate.principles.size }
    ReasonsScreen(
        principleTemplate = principleTemplate,
        tradeInfo = tradeInfo,
        pagerState = pagerState,
        modifier = Modifier.background(HedgeColor.Neutral.BackgroundDefault),
        onClickBack = {},
        onClickDone = {},
        onClickAddImage = {},
        onClickAddLink = {},
        onClickAddMention = {},
        onClickImage = {},
        onClickDeleteImage = {},
        onClickDeleteLink = {},
        onAdherenceChanged = {},
        onReasonChanged = { reason = it },
    )
}