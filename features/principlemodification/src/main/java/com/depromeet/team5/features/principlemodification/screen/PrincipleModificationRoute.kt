package com.depromeet.team5.features.principlemodification.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.navigation.request.PrincipleGraphViewModel
import com.depromeet.team5.features.principlemodification.R
import com.depromeet.team5.features.principlemodification.event.Event
import kotlinx.coroutines.launch


@Composable
fun PrincipleModificationRoute(
    modifier: Modifier = Modifier,
    onBackClicked: (Boolean) -> Unit = {},
    onShowErrorToast: (Throwable) -> Unit,
    onShowToast: (String) -> Unit,
    onShowNoIconToast: (String) -> Unit,
    graphViewModel: PrincipleGraphViewModel
) {
    val viewModel: PrincipleModificationViewModel = hiltViewModel(
        creationCallback = { factory: PrincipleModificationViewModel.Factory ->
            factory.create(
                principleId = graphViewModel.principleId,
                myPrincipleGroup = graphViewModel.myPrincipleGroup,
                modificationType = graphViewModel.modificationType
            )
        }
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val groupName by viewModel.groupNameStateFlow.stateFlow.collectAsStateWithLifecycle()
    val principle by viewModel.principleStateFlow.stateFlow.collectAsStateWithLifecycle()
    val content by viewModel.contentStateFlow.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .collect { event ->
                    when (event) {
                        Event.Complete -> {
                            onShowToast(context.getString(R.string.principle_modification_toast_message))
                            onBackClicked(true)
                        }

                        is Event.ShowErrorToast -> {
                            onShowErrorToast(event.throwable)
                        }
                    }
                }
        }
    }

    PrincipleModificationScreen(
        groupName = groupName ?: "",
        principle = principle ?: "",
        content = content ?: "",
        modifier = modifier,
        onBackClicked = onBackClicked,
        onClickedConfirmButton = {
            viewModel.modifyPrinciple()
        },
        onUpdatedPrinciple = { newPrinciple ->
            viewModel.principleStateFlow.update { newPrinciple }
        },
        onUpdatedContent = { newContent ->
            viewModel.contentStateFlow.update { newContent }
        },
        onShowNoIconToast = { message ->
            onShowNoIconToast(message)
        }
    )
}

@Composable
private fun PrincipleModificationScreen(
    groupName: String,
    principle: String,
    content: String,
    modifier: Modifier = Modifier,
    onClickedConfirmButton: () -> Unit,
    onUpdatedPrinciple: (String) -> Unit,
    onUpdatedContent: (String) -> Unit,
    onBackClicked: (Boolean) -> Unit = {},
    onShowNoIconToast: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE)
    ) {
        Topbar(
            title = groupName,
            enabled = principle.isNotEmpty() || content.isNotEmpty(),
            onClickedButton = onClickedConfirmButton,
            onBackClicked = onBackClicked
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        DisableMarkerBasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            value = principle,
            onValueChange = { newPrinciple ->
                if (newPrinciple.length > 40) {
                    onShowNoIconToast(context.getString(R.string.principle_modification_limit_group_name))
                } else {
                    onUpdatedPrinciple(newPrinciple)
                }
            },
            textStyle = HedgeTypography.Headline1.SemiBold.copy(
                color = HedgeColor.Text.Title
            ),
            decorationBox = { innerTextField ->
                if (principle.isEmpty()) {
                    Text(
                        text = stringResource(R.string.principle_modification_principle_hint),
                        style = HedgeTypography.Headline1.SemiBold,
                        color = HedgeColor.Text.Assistive
                    )
                }

                innerTextField()
            }
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            color = HedgeColor.Neutral.BackgroundSecondary
        )

        DisableMarkerBasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            value = content,
            onValueChange = { newContent ->
                onUpdatedContent(newContent)
            },
            decorationBox = { innerTextField ->
                if (content.isEmpty()) {
                    Text(
                        text = stringResource(R.string.principle_modification_content_hint),
                        style = HedgeTypography.Body3.Regular,
                        color = HedgeColor.Text.Assistive
                    )
                }

                innerTextField()
            }
        )
    }
}

@Composable
private fun Topbar(
    title: String,
    enabled: Boolean = false,
    modifier: Modifier = Modifier,
    onClickedButton: () -> Unit,
    onBackClicked: (Boolean) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HedgeColor.WHITE)
            .padding(start = 4.dp, top = 2.dp, end = 16.dp, bottom = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .clickable(
                    enabled = true,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onBackClicked(false)
                },
            imageVector = HedgeIcon.ArrowLeftThick,
            contentDescription = null,
            colorFilter = ColorFilter.tint(HedgeColor.Text.Primary)
        )

        Spacer(modifier.weight(1f))

        Text(
            text = title,
            style = HedgeTypography.Body3.SemiBold,
            color = HedgeColor.Text.Primary
        )

        Spacer(modifier.weight(1f))

        HedgeButton.Text(
            text = stringResource(R.string.confirm),
            enabled = enabled,
            forceClickable = false,
            imageVector = null,
            size = HedgeButton.Text.Size.Large,
            onClick = onClickedButton
        )
    }
}

@Composable
fun DisableMarkerBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = HedgeTypography.Body3.Regular
        .copy(color = HedgeColor.Text.Title),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(HedgeColor.Brand.Darken),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() },
) {

    val transparentSelectionColors = remember {
        TextSelectionColors(
            handleColor = Color.Transparent,
            backgroundColor = Color.Transparent
        )
    }

    CompositionLocalProvider(
        LocalTextSelectionColors provides transparentSelectionColors
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            cursorBrush = cursorBrush,
            decorationBox = decorationBox
        )
    }
}

@Preview
@Composable
private fun PrincipleModificationScreenPreview(
    modifier: Modifier = Modifier
) {
    PrincipleModificationScreen(
        groupName = "이건 좀 지키자 제발 이건 좀 지키자",
        principle = "종목 선택 시 최근 매출액 확인!!!!",
        content = "상승장에서 눌림목 나오면 지지선 나올 때까지 기다렸다가 분할 매수하자. 몰빵은 절대 금지다!!!!!",
        modifier = modifier,
        onClickedConfirmButton = {},
        onUpdatedPrinciple = {},
        onUpdatedContent = {},
        onBackClicked = {},
        onShowNoIconToast = {}
    )
}

@Preview
@Composable
private fun TopbarPreview(
    modifier: Modifier = Modifier
) {
    Topbar(
        title = "이건 좀 지키자 제발 이건 좀 지키자",
        onClickedButton = {}
    )
}