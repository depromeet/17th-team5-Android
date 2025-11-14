package com.depromeet.team5.features.principlegroupmodification.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.FinishType
import com.depromeet.team5.core.domain.monad.BaseEvent
import com.depromeet.team5.features.principlegroupmodification.R
import kotlinx.coroutines.launch
import com.depromeet.team5.core.ui.R as UiR


@Composable
fun PrincipleGroupModificationRoute(
    modifier: Modifier = Modifier,
    viewModel: PrincipleGroupModificationViewModel = hiltViewModel(),
    onShowToast: (String) -> Unit,
    onShowNoIconToast: (String) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    onBackPressed: (Boolean) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val groupName by viewModel.groupNameState.stateFlow.collectAsStateWithLifecycle()
    val thumbnail by viewModel.thumbnailState.stateFlow.collectAsStateWithLifecycle()

    val isConfirmButtonEnabled by remember {
        derivedStateOf {
            if (viewModel.isNewPrincipleGroup()) {
                groupName.isNotEmpty() && thumbnail.isNotEmpty()
            } else {
                viewModel.distinct(groupName, thumbnail)
            }
        }
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch {
            viewModel.eventFlow
                .flowWithLifecycle(lifecycleOwner.lifecycle)
                .collect { event ->
                    when (event) {
                        is BaseEvent.Error -> {
                            event.throwable?.let {
                                onShowErrorToast(it)
                            }
                            onBackPressed(false)
                        }
                        is BaseEvent.Finish<FinishType> -> {
                            val message = when (event.result) {
                                FinishType.CREATION -> {
                                    context.getString(
                                        UiR.string.principle_group_modification_creation
                                    )
                                }
                                FinishType.MODIFICATION -> {
                                    context.getString(
                                        UiR.string.principle_group_modification_modification
                                    )
                                }
                                else -> ""
                            }

                            onShowToast(message)
                            onBackPressed(true)
                        }
                        else -> {}
                    }
                }
        }
    }

    PrincipleGroupModificationScreen(
        groupName = groupName,
        selectedEmoji = thumbnail,
        isConfirmButtonEnabled = isConfirmButtonEnabled,
        modifier = modifier,
        onClickedEmoji = { selectedEmoji ->
            viewModel.updateThumbnail(selectedEmoji)
        },
        onClickedConfirmButton = {
            viewModel.upsertPrincipleGroup()
        },
        onUpdateGroupName = { newGroupName ->
            viewModel.updateGroupName(newGroupName)
        },
        onShowToast = onShowNoIconToast,
        onBackPressed = onBackPressed
    )
}

@Composable
fun PrincipleGroupModificationScreen(
    groupName: String,
    selectedEmoji: String,
    isConfirmButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClickedEmoji: (String) -> Unit,
    onClickedConfirmButton: () -> Unit,
    onUpdateGroupName: (String) -> Unit,
    onShowToast: (String) -> Unit,
    onBackPressed: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val emojiList = stringArrayResource(R.array.emoji_array)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE)
            .imePadding()
    ) {
        HedgeTopBar(
            title = {
                Text(
                    text = stringResource(UiR.string.principle_group_modification_title),
                    style = HedgeTypography.Body3.SemiBold,
                    color = HedgeColor.Text.Primary
                )
            },
            back = {
                Image(
                    imageVector = HedgeIcon.ArrowLeftThick,
                    contentDescription = null
                )
            },
            action = {
                HedgeButton.Text(
                    text = stringResource(id = UiR.string.completion),
                    enabled = isConfirmButtonEnabled,
                    forceClickable = isConfirmButtonEnabled,
                    imageVector = null,
                    onClick = onClickedConfirmButton
                )
            },
            onClickBack = {
                onBackPressed(false)
            }
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        DisableMarkerBasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 18.dp),
            value = groupName,
            onValueChange = { new ->
                if (new.length > 20) {
                    onShowToast(
                        context.getString(
                            UiR.string.principle_group_modification_limit_group_name_count
                        )
                    )
                } else {
                    onUpdateGroupName(new)
                }
            },
            singleLine = true,
            textStyle = HedgeTypography.Body2.SemiBold.copy(
                color = HedgeColor.Text.Title
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if (groupName.isEmpty()) {
                            Text(
                                text = stringResource(UiR.string.principle_group_modification_placeholder),
                                style = HedgeTypography.Body2.SemiBold,
                                color = HedgeColor.Text.Assistive
                            )

                        } else {
                            innerTextField()
                        }
                    }

                    Text(
                        modifier = Modifier
                            .padding(start = 18.dp),
                        text = "${groupName.length} / 20",
                        style = HedgeTypography.Label2.Medium,
                        color = HedgeColor.Text.Assistive
                    )
                }
            }
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .background(
                    color = if (groupName.isNotEmpty()) {
                        HedgeColor.Brand.Primary
                    } else {
                        HedgeColor.Neutral.BackgroundSecondary
                    }
                ),
            thickness = 1.dp,
            color = HedgeColor.Text.Disabled
        )

        Text(
            modifier = Modifier
                .padding(start = 20.dp, top = 30.dp, bottom = 10.dp),
            text = stringResource(UiR.string.principle_group_modification_icon_title),
            style = HedgeTypography.Body2.Medium,
            color = HedgeColor.Text.Title
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(start = 20.dp, top = 4.dp, end = 20.dp)
        ) {
            items(
                items = emojiList,
                key = { it }
            ) { emoji ->
                GridItem(
                    emoji = emoji,
                    isSelected = emoji == selectedEmoji,
                    onClickedEmoji = onClickedEmoji
                )
            }
        }
    }
}

@Composable
private fun GridItem(
    emoji: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClickedEmoji: (String) -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                enabled = true,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClickedEmoji(emoji)
            }
            .border(
                width = 2.dp,
                color = if (isSelected) {
                    HedgeColor.Brand.Primary
                } else {
                    HedgeColor.Transparent
                },
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(7.dp),
            text = emoji,
            fontSize = 26.sp,
            textAlign = TextAlign.Center
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
fun PrincipleGroupModificationScreenPreview() {
    PrincipleGroupModificationScreen(
        groupName = "",
        selectedEmoji = "",
        isConfirmButtonEnabled = false,
        onClickedConfirmButton = {},
        onUpdateGroupName = {},
        onClickedEmoji = {},
        onShowToast = {},
        onBackPressed = {}
    )
}

@Preview(showBackground = true)
@Composable
fun GridItemPreView() {
    GridItem(
        emoji = "\uD83D\uDE24",
        isSelected = true
    ) { }
}