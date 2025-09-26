package com.depromeet.team5.feature.reasons

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.model.request.RequestViewModel
import com.depromeet.team5.feature.reasons.ui.AnalysisPip
import com.depromeet.team5.feature.reasons.ui.AutoScrollTextField
import com.depromeet.team5.feature.reasons.ui.EmotionBottomSheet
import com.depromeet.team5.feature.reasons.ui.PrincipleBottomSheet

/*
* todo
*  config change/process kill
*  refactoring
* */
@Composable
fun ReasonRoute(
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
    requestViewModel: RequestViewModel = hiltViewModel(),
    viewModel: ReasonsViewModel = hiltViewModel(),
) {
    val principles by viewModel.principles.collectAsStateWithLifecycle()
    val selectedEmotion by viewModel.selectedEmotion.collectAsStateWithLifecycle()
    val tradeInfo by viewModel.tradeInfo.collectAsStateWithLifecycle()
    val reason by viewModel.reason.collectAsStateWithLifecycle()
    val analysisReport by viewModel.analysisReport.collectAsStateWithLifecycle()

    ReasonScreen(
        tradeInfo = tradeInfo,
        selectedEmotion = selectedEmotion,
        principles = principles,
        reason = reason,
        analysisReport = analysisReport,
        onClickBack = onClickBack,
        onClickDone = {
            /* todo */
        },
        onClickEditTradeInfo = {
            /* todo */
        },
        onReasonChanged = viewModel::onReasonChanged,
        onEmotionChanged = viewModel::onEmotionChanged,
        onPrincipleCheckedChanged = viewModel::onPrincipleCheckedChanged,
        modifier = modifier
    )
}

@Composable
private fun ReasonScreen(
    principles: List<Principle>,
    selectedEmotion: Emotion?,
    tradeInfo: TradeInfo,
    reason: TextFieldValue,
    analysisReport: String?,
    onClickBack: () -> Unit,
    onClickDone: () -> Unit,
    onEmotionChanged: (Emotion) -> Unit,
    onPrincipleCheckedChanged: (List<Principle>) -> Unit,
    onClickEditTradeInfo: () -> Unit,
    onReasonChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val isImeVisible = WindowInsets.ime.getBottom(density) > 0
    var showPrinciplesBottomSheet by remember { mutableStateOf(false) }
    var showEmotionBottomSheet by remember { mutableStateOf(false) }
    var showAnalysisPip by remember { mutableStateOf(false) }

    val selectedPrincipleCount = principles.count { it.checked }
    val showToolBox by remember(showPrinciplesBottomSheet, showEmotionBottomSheet, isImeVisible) {
        derivedStateOf { (showPrinciplesBottomSheet || showEmotionBottomSheet || showAnalysisPip || isImeVisible).not() }
    }

    Box(
        modifier = modifier
            .background(HedgeColor.Neutral.BackgroundDefault)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)

    ) {
        EmotionBottomSheet(
            selected = selectedEmotion ?: Emotion.Anxious,
            showEmotionBottomSheet = showEmotionBottomSheet,
            onClickCancel = { showEmotionBottomSheet = false },
            onClickDone = {
                showEmotionBottomSheet = false
                onEmotionChanged(it)
            },
            onDismissRequest = { showEmotionBottomSheet = false },
            modifier = Modifier
        )

        PrincipleBottomSheet(
            initialPrinciples = principles,
            showPrinciplesBottomSheet = showPrinciplesBottomSheet,
            onClickCancel = { showPrinciplesBottomSheet = false },
            onClickDone = {
                onPrincipleCheckedChanged(it)
                showPrinciplesBottomSheet = false
            },
            onDismissRequest = { showPrinciplesBottomSheet = false },
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars)
        )

        AnalysisPip(
            showAnalysisPip = showAnalysisPip,
            analysisReport = analysisReport,
            onDismissRequest = { showAnalysisPip = false },
            onClickCancel = { showAnalysisPip = false },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 10.dp)
        )

        ReasonScreenContents(
            tradeInfo = tradeInfo,
            selectedEmotion = selectedEmotion,
            selectedPrincipleCount = selectedPrincipleCount,
            reason = reason,
            showToolBox = showToolBox,
            onClickBack = onClickBack,
            onClickDone = onClickDone,
            onClickEditTradeInfo = onClickEditTradeInfo,
            onClickAI = { showAnalysisPip = true },
            onClickEmotion = { showEmotionBottomSheet = true },
            onClickPrinciples = { showPrinciplesBottomSheet = true },
            onReasonChanged = onReasonChanged,
            modifier = Modifier,
        )
    }
}

@Composable
private fun ReasonScreenContents(
    tradeInfo: TradeInfo,
    selectedEmotion: Emotion?,
    selectedPrincipleCount: Int,
    reason: TextFieldValue,
    showToolBox: Boolean,
    onClickBack: () -> Unit,
    onClickDone: () -> Unit,
    onClickEditTradeInfo: () -> Unit,
    onClickAI: () -> Unit,
    onClickEmotion: () -> Unit,
    onClickPrinciples: () -> Unit,
    onReasonChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val isImeVisible = WindowInsets.ime.getBottom(density) > 0
    var toolboxContainerSize by remember { mutableStateOf(IntSize.Zero) }
    val textFieldInsetFromToolboxDp = with(density) { (toolboxContainerSize.height * 0.9f).toDp() }
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(scrollState)
        ) {
            HedgeTopBar(
                onClickBack = onClickBack,
                action = {
                    HedgeButton.Text(
                        text = stringResource(id = R.string.done),
                        imageVector = null,
                        onClick = onClickDone,
                    )
                }
            )
            TradeInfo(
                tradeInfo = tradeInfo,
                onClickEditTradeInfo = onClickEditTradeInfo,
            )
            Spacer(Modifier.size(16.dp))
            Image(
                painter = painterResource(if (tradeInfo.orderType == OrderType.BUY) R.drawable.buy else R.drawable.sell),
                contentDescription = "chart",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(Modifier.size(16.dp))
            TagContainer(
                selectedEmotion = selectedEmotion,
                selectedPrincipleCount = selectedPrincipleCount,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Spacer(Modifier.size(8.dp))

            AutoScrollTextField(
                content = reason,
                onValueChange = onReasonChanged,
                isImeVisible = isImeVisible,
                modifier = Modifier
                    .padding(bottom = if (isImeVisible) 8.dp else textFieldInsetFromToolboxDp)
            )
        }
        if (showToolBox) {
            ToolboxContainer(
                onClickAI = onClickAI,
                onClickEmotion = onClickEmotion,
                onClickPrinciples = onClickPrinciples,
                modifier
                    .align(Alignment.BottomCenter)
                    .onSizeChanged { toolboxContainerSize = it }
            )
        }
    }
}

@Composable
private fun TradeInfo(
    tradeInfo: TradeInfo,
    onClickEditTradeInfo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Icon(
                painter = painterResource(id = tradeInfo.logoDrawableRes),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(22.dp),
            )
            Text(
                text = tradeInfo.companyName,
                color = HedgeColor.Text.Title,
                style = HedgeTypography.Body3.SemiBold,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.trade_info,
                    tradeInfo.price,
                    tradeInfo.volume,
                    stringResource(if (tradeInfo.orderType == OrderType.BUY) R.string.buy else R.string.sell)
                ),
                color = HedgeColor.Text.Title,
                style = HedgeTypography.Headline1.SemiBold,
            )
            Icon(
                imageVector = HedgeIcon.Pencil,
                contentDescription = "edit",
                tint = HedgeColor.Text.Assistive,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = onClickEditTradeInfo,
                        interactionSource = remember { MutableInteractionSource() },
                    )
                    .padding(3.dp)
            )
        }
        Text(
            text = tradeInfo.orderDate,
            color = HedgeColor.Text.Alternative,
            style = HedgeTypography.Label2.Regular,
        )
    }
}

@Composable
private fun TagContainer(
    selectedEmotion: Emotion?,
    selectedPrincipleCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        selectedEmotion?.let {
            Tag(
                text = stringResource(id = it.labelRes),
                icon = painterResource(id = it.iconRes)
            )
        }
        if (selectedPrincipleCount > 0) {
            Tag(
                text = stringResource(R.string.selected_principle_count, selectedPrincipleCount),
                icon = painterResource(R.drawable.ic_book)
            )
        }
    }
}

@Composable
private fun Tag(
    text: CharSequence,
    icon: Painter,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = HedgeColor.Neutral.BackgroundSecondary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(start = 6.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = "emotion",
            tint = HedgeColor.Text.Title,
        )
        if (text is AnnotatedString) {
            Text(
                text = text,
                color = HedgeColor.Text.Primary,
                style = HedgeTypography.Caption1.Semibold,
            )
        } else {
            Text(
                text = text.toString(),
                color = HedgeColor.Text.Primary,
                style = HedgeTypography.Caption1.Semibold,
            )
        }
    }
}


@Composable
private fun ToolboxContainer(
    onClickAI: () -> Unit,
    onClickEmotion: () -> Unit,
    onClickPrinciples: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    0f to Color.Transparent,
                    0.3577f to HedgeColor.Neutral.BackgroundDefault,
                ),
            )
            .padding(top = 36.dp, bottom = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Toolbox(
            onClickAI = onClickAI,
            onClickEmotion = onClickEmotion,
            onClickPrinciples = onClickPrinciples,
        )
    }
}

@Composable
private fun Toolbox(
    onClickAI: () -> Unit,
    onClickEmotion: () -> Unit,
    onClickPrinciples: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(29.dp)
    Row(
        modifier = modifier
            .border(
                width = (1.2).dp,
                color = HedgeColor.Neutral.BackgroundDefault,
                shape = shape
            )
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 60.dp,
                    spread = 0.dp,
                    color = Color(0xFF0D0F26).copy(alpha = 0.14f),
                    offset = DpOffset(x = 0.dp, y = 12.dp)
                )
            )
            .background(
                color = HedgeColor.Neutral.BackgroundDefault.copy(alpha = 0.7f),
                shape = shape,
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ToolIcon(
            painter = painterResource(R.drawable.ic_twinkle),
            contentDescription = "ai assistant",
            onClick = onClickAI
        )
        ToolIcon(
            painter = painterResource(R.drawable.ic_add_emotion),
            contentDescription = "emotion",
            onClick = onClickEmotion
        )
        ToolIcon(
            painter = painterResource(R.drawable.ic_checklist),
            contentDescription = "principles",
            onClick = onClickPrinciples
        )
    }
}

@Composable
private fun ToolIcon(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .clip(RoundedCornerShape(9.dp))
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    color = HedgeColor.GREY_OPACITY_200
                )
            )
            .padding(8.dp),
    )
}

@Composable
@Preview
private fun ReasonsScreenPreview() {

    var reason by remember { mutableStateOf(TextFieldValue("")) }

    ReasonScreenContents(
        modifier = Modifier.background(HedgeColor.Neutral.BackgroundDefault),
        tradeInfo = ReasonsViewModel.dummyTradeInfo,
        selectedEmotion = null,
        selectedPrincipleCount = 0,
        reason = reason,
        showToolBox = true,
        onClickBack = {},
        onClickDone = {},
        onClickEditTradeInfo = {},
        onClickAI = {},
        onClickEmotion = {},
        onClickPrinciples = {},
        onReasonChanged = { reason = it },
    )
}