package com.depromeet.team5.features.newprinciples.screen.addprinciples

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import kotlinx.coroutines.launch
import com.depromeet.team5.core.ui.R as UiR


@Composable
fun AddPrinciplesRoute(
    modifier: Modifier = Modifier,
    onShowErrorToast: (String) -> Unit,
    onShowToast: (String) -> Unit,
    onBackPressed: () -> Unit
) {

    AddPrinciplesScreen(
        modifier = modifier,
        groupName = "",
        enabled = false,
        principles = listOf(),
        onTextChanged = { page, new -> },
        onBackPressed = onBackPressed
    )
}

@Composable
fun AddPrinciplesScreen(
    groupName: String,
    enabled: Boolean,
    principles: List<String>,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onTextChanged: (Int, String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { principles.size }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = HedgeColor.WHITE)
    ) {
        HedgeTopBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    text = groupName,
                    style = HedgeTypography.Body3.SemiBold,
                    color = HedgeColor.Text.Primary
                )
            },
            action = {
                HedgeButton.Text(
                    text = stringResource(UiR.string.next),
                    imageVector = null,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    enabled = enabled,
                    forceClickable = false
                )
            },
            onClickBack = onBackPressed
        )

        AddPrincipleContent(
            pagerState = pagerState,
            principles = principles,
            onTextChanged = onTextChanged
        )
    }
}

@Composable
private fun AddPrincipleContent(
    pagerState: PagerState,
    principles: List<String>,
    modifier: Modifier = Modifier,
    onTextChanged: (Int, String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = HedgeColor.Brand.Primary)
                ) {
                    append("${pagerState.currentPage + 1}")
                }

                append("/${principles.size}")

            },
            style = HedgeTypography.Label1.Regular,
            color = HedgeColor.Text.Assistive,
            modifier = Modifier
                .padding(start = 20.dp, top = 15.dp)
                .then(
                    Modifier
                        .background(
                            color = HedgeColor.Neutral.BackgroundSecondary,
                            shape = CircleShape
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 2.dp
                        )
                )
        )

        HorizontalPager(
            modifier = Modifier.padding(top = 3.dp),
            state = pagerState,
            beyondViewportPageCount = 1,
            userScrollEnabled = false
        ) { page ->

            PrinciplePage(
                page = page,
                content = principles[page],
                onTextChanged = onTextChanged
            )
        }
    }
}

@Composable
private fun PrinciplePage(
    page: Int,
    content: String,
    modifier: Modifier = Modifier,
    onTextChanged: (Int, String) -> Unit,
    viewModel: AddPrinciplePageViewModel = hiltViewModel()
) {
    val title by viewModel.title.collectAsStateWithLifecycle()
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = HedgeColor.WHITE)
            .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        DisableMarkerBasicTextField(
            modifier = Modifier.onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
            value = title,
            onValueChange = { new ->
                viewModel.updateTitle(new)
                onTextChanged(page, new)
            },
            decorationBox = { innerTextField ->
                if (!isFocused) {
                    Text(
                        text = stringResource(UiR.string.principle_modification_principle_hint),
                        style = HedgeTypography.Headline1.SemiBold,
                        color = HedgeColor.Text.Assistive
                    )
                }

                innerTextField()
            }
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            thickness = 1.dp,
            color = HedgeColor.Neutral.BackgroundSecondary
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = content,
            style = HedgeTypography.Body3.Regular,
            color = HedgeColor.Text.Title
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


@Preview(showBackground = true)
@Composable
fun AddPrinciplesScreenPreview() {

    AddPrinciplesScreen(
        groupName = "이것 좀 지키자 제발",
        enabled = false,
        principles = listOf("test1", "test2"),
        onTextChanged = { page, new ->
        },
        onBackPressed = {},
    )
}
