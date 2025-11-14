package com.depromeet.team5.feature.reasons.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ripple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.Memo
import com.depromeet.team5.core.ui.HedgeModal
import com.depromeet.team5.core.ui.util.toMonthDayOrRaw
import com.depromeet.team5.feature.reasons.R
import com.depromeet.team5.feature.reasons.RetrospectionDetailViewModel.Companion.ADD_MEMO_ID
import com.depromeet.team5.feature.reasons.RetrospectionDetailViewModel.Companion.INVALID_ID
import com.depromeet.team5.feature.reasons.previewRetrospection
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoBottomSheet(
    showBottomSheet: Boolean,
    memos: List<Memo>,
    onClickCreateMemo: (String) -> Unit,
    onClickUpdateMemo: (Int, String) -> Unit,
    onClickDeleteMemo: (Int) -> Unit,
    onClickClose: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = HedgeColor.Neutral.BackgroundDefault,
    dragHandle: @Composable (() -> Unit)? = null,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    if (showBottomSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            containerColor = containerColor,
            dragHandle = dragHandle,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            MemoBottomSheetContents(
                memos = memos,
                onCreateMemo = onClickCreateMemo,
                onUpdateMemo = onClickUpdateMemo,
                onDeleteMemo = onClickDeleteMemo,
                onClickClose = onClickClose,
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(max = screenHeight * 0.76f)
            )
        }
    }

}

@Composable
private fun MemoBottomSheetContents(
    memos: List<Memo>,
    onCreateMemo: (String) -> Unit,
    onUpdateMemo: (Int, String) -> Unit,
    onDeleteMemo: (Int) -> Unit,
    onClickClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val isImeVisible = WindowInsets.ime.getBottom(density) > 0
    var showPopupMenuId by remember { mutableIntStateOf(INVALID_ID) }
    var showDeleteModalId by remember { mutableIntStateOf(INVALID_ID) }
    var activatedMemoId by remember { mutableIntStateOf(INVALID_ID) }
    var addMemoContent by remember { mutableStateOf("") }
    val memoContents = remember(memos) { mutableStateListOf(*memos.map { it.content }.toTypedArray()) }
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 ||
                listState.firstVisibleItemScrollOffset > 0
        }
    }

    HedgeModal(
        showModal = showDeleteModalId >= 0,
        title = stringResource(R.string.delete_memo_title),
        description = stringResource(R.string.delete_memo_description),
        submitButton = stringResource(R.string.delete) to {
            onDeleteMemo(showDeleteModalId)
            showDeleteModalId = INVALID_ID
        },
        cancelButton = stringResource(R.string.cancel) to {
            showDeleteModalId = INVALID_ID
        },
        onDismissRequest = { showDeleteModalId = INVALID_ID }
    )

    Column(
        modifier = modifier,
    ) {
        val dividerColor = HedgeColor.Neutral.BackgroundSecondary
        val dividerModifier = if (isScrolled) Modifier
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = dividerColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            } else Modifier
        Row(
            modifier = dividerModifier
                .padding(start = 20.dp, end = 10.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = stringResource(R.string.memo),
                color = HedgeColor.Text.Primary,
                style = HedgeTypography.Body1.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(R.drawable.ic_close_circle),
                contentDescription = "close",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(
                        onClick = onClickClose,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            bounded = false,
                            radius = 19.dp
                        ),
                    )

            )
        }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(top = 6.dp),
        ) {
            if (activatedMemoId == INVALID_ID || activatedMemoId == ADD_MEMO_ID) {
                item {
                    AddMemoRow(
                        content = addMemoContent,
                        createdAt = LocalDate.now().toString(),
                        activated = activatedMemoId == ADD_MEMO_ID,
                        isLastAt = memos.isEmpty(),
                        onClick = { activatedMemoId = ADD_MEMO_ID },
                        onMemoContentChanged = { addMemoContent = it },
                        modifier = Modifier
                    )
                }
            }
            itemsIndexed(
                items = memos,
                key = { _, item -> item.memoId },
            ) { idx, memo ->
                MemoRow(
                    content = memoContents[idx],
                    createdAt = memo.createdAt.toMonthDayOrRaw(),
                    activated = memo.memoId == activatedMemoId,
                    showPopupMenu = memo.memoId == showPopupMenuId,
                    isLastAt = idx == memos.size - 1,
                    hasAnyActivated = activatedMemoId != INVALID_ID,
                    onMemoContentChanged = { memoContents[idx] = it },
                    onClickMenu = { showPopupMenuId = memo.memoId },
                    onDismissMenu = { showPopupMenuId = INVALID_ID },
                    onClickUpdateMemo = {
                        activatedMemoId = memo.memoId
                        showPopupMenuId = INVALID_ID
                    },
                    onClickDeleteMemo = {
                        showDeleteModalId = memo.memoId
                        showPopupMenuId = INVALID_ID
                    },
                    modifier = Modifier
                )
            }
        }

        if (isImeVisible) {
            val dividerColor = HedgeColor.Neutral.BackgroundSecondary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        drawLine(
                            color = dividerColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = strokeWidth
                        )
                    }
                    .padding(top = (8.5).dp, bottom = (8.5).dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                HedgeButton.Text(
                    text = stringResource(id = if (activatedMemoId == ADD_MEMO_ID) R.string.remain else R.string.edit_complete),
                    onClick = {
                        if (activatedMemoId == ADD_MEMO_ID) {
                            if (addMemoContent.isNotEmpty() && addMemoContent.isNotBlank()) {
                                onCreateMemo(addMemoContent)
                                addMemoContent = ""
                            }
                        } else {
                            onUpdateMemo(activatedMemoId, memoContents[memos.indexOfFirst { it.memoId == activatedMemoId }])
                        }
                        activatedMemoId = INVALID_ID
                    },
                    imageVector = null,
                )
            }
        }
    }
}

@Composable
private fun AddMemoRow(
    content: String,
    createdAt: String,
    activated: Boolean = false,
    isLastAt: Boolean,
    onClick: () -> Unit,
    onMemoContentChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    enabled = activated.not(),
                    onClick = onClick,
                    interactionSource = remember { MutableInteractionSource() },
                )
                .padding(4.dp),

            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = if (activated) R.drawable.ic_note_circle else R.drawable.ic_add_circle),
                contentDescription = "add memo",
                tint = Color.Unspecified,
            )
            Spacer(Modifier.size(13.dp))
            Text(
                text = if (activated) createdAt.toMonthDayOrRaw() else stringResource(R.string.add_memo),
                color = if (activated) HedgeColor.Brand.Primary else HedgeColor.Text.Alternative,
                style = if (activated) HedgeTypography.Body3.SemiBold else HedgeTypography.Body3.Medium,
            )
        }
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(horizontal = 20.dp)
        ) {
            VerticalDivider(
                thickness = (1.5).dp,
                color = HedgeColor.GREY_200,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = (14.25).dp, end = (14.25).dp, top = 4.dp, bottom = 8.dp)
                    .defaultMinSize(minHeight = 17.dp)
                    .alpha(if (isLastAt) 0f else 1f)
            )
            Spacer(Modifier.size(12.dp))
            Column {
                if (activated) {
                    MemoText(
                        content = content,
                        onMemoContentChanged = onMemoContentChanged,
                        modifier = Modifier
                    )
                }
                Spacer(Modifier.size(20.dp))
            }
        }
    }
}

@Composable
private fun MemoRow(
    content: String,
    createdAt: String,
    activated: Boolean,
    showPopupMenu: Boolean,
    isLastAt: Boolean,
    hasAnyActivated: Boolean,
    onMemoContentChanged: (String) -> Unit,
    onDismissMenu: () -> Unit,
    onClickMenu: () -> Unit,
    onClickUpdateMemo: () -> Unit,
    onClickDeleteMemo: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val activatedTitleColor = if (activated) HedgeColor.Brand.Primary else HedgeColor.Text.Primary
    val activatedOpacity = if (hasAnyActivated && !activated) 0.3f else 1f
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .alpha(activatedOpacity),
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_note_circle),
                contentDescription = "add memo",
                tint = Color.Unspecified,
            )
            Spacer(Modifier.size(13.dp))
            Text(
                text = createdAt,
                color = activatedTitleColor,
                style = HedgeTypography.Body3.SemiBold,
                modifier = Modifier.weight(1f)
            )
            if (hasAnyActivated.not()) {
                Box {
                    Icon(
                        imageVector = HedgeIcon.Menu,
                        contentDescription = "add memo",
                        tint = HedgeColor.Text.Disabled,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable(
                                onClick = onClickMenu,
                                interactionSource = remember { MutableInteractionSource() },
                            )
                    )
                    MemoDropDownMenu(
                        expanded = showPopupMenu,
                        onClickedModifyButton = onClickUpdateMemo,
                        onClickedRemoveButton = onClickDeleteMemo,
                        onDismissRequest = onDismissMenu
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .height(IntrinsicSize.Min)
        ) {
            VerticalDivider(
                thickness = (1.5).dp,
                color = HedgeColor.GREY_200,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = (14.25).dp, end = (14.25).dp, top = 4.dp, bottom = 8.dp)
                    .defaultMinSize(minHeight = 17.dp)
                    .alpha(if (isLastAt) 0f else 1f)
            )
            Spacer(Modifier.size(12.dp))
            Column {
                MemoText(
                    content = content,
                    readOnly = activated.not(),
                    onMemoContentChanged = onMemoContentChanged,
                    modifier = Modifier
                )
                Spacer(Modifier.size(if (activated) 20.dp else 10.dp))
            }
        }
    }
}

@Composable
private fun MemoText(
    content: String,
    readOnly: Boolean = false,
    onMemoContentChanged: (String) -> Unit,
    decorationColor: Color = HedgeColor.Brand.Primary,
    modifier: Modifier,
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val decorationModifier = if (readOnly) Modifier else Modifier.drawBehind {
        val strokeWidth = 1.5.dp.toPx()
        val y = size.height - strokeWidth / 2
        drawLine(
            color = decorationColor,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = strokeWidth
        )
    }

    LaunchedEffect(readOnly) {
        if (!readOnly) {
            focusRequester.requestFocus()
        }
    }
    //todo 자동스크롤
    BasicTextField(
        value = content,
        onValueChange = onMemoContentChanged,
        cursorBrush = SolidColor(decorationColor),
        readOnly = readOnly,
        textStyle = HedgeTypography.Body3.Regular.copy(
            color = HedgeColor.Text.Primary,
        ),
        modifier = modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .then(decorationModifier)
                .padding(bottom = 10.dp)
        ) {
            if (content.isEmpty()) {
                Text(
                    text = stringResource(R.string.add_memo),
                    style = HedgeTypography.Body3.Regular,
                    color = HedgeColor.Text.Assistive
                )
            }
            innerTextField()
        }
    }
}

//todo custom
@Composable
private fun MemoDropDownMenu(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onClickedModifyButton: () -> Unit,
    onClickedRemoveButton: () -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        modifier = modifier
            .background(HedgeColor.Neutral.BackgroundDefault),
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(16.dp),
        expanded = expanded,
        containerColor = HedgeColor.Neutral.BackgroundDefault,
        onDismissRequest = onDismissRequest,
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.edit),
                    style = HedgeTypography.Body3.Medium,
                    color = HedgeColor.Text.Primary
                )
            },
            onClick = onClickedModifyButton,
            trailingIcon = {
                Icon(
                    imageVector = HedgeIcon.Pencil,
                    contentDescription = "edit",
                    tint = HedgeColor.Text.Alternative
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
                Icon(
                    imageVector = HedgeIcon.Trash,
                    contentDescription = "delete",
                    tint = HedgeColor.Feedback.Error
                )
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun MemoBottomSheetContentsPreview() {
    MemoBottomSheetContents(
        memos = previewRetrospection.memos,
        onCreateMemo = {},
        onUpdateMemo = { _, _ -> },
        onDeleteMemo = {},
        onClickClose = {}
    )
}


@Composable
@Preview
private fun MemoBottomSheetPreview() {
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
            .background(HedgeColor.Neutral.BackgroundDefault)
    ) {
        MemoBottomSheet(
            showBottomSheet = true,
            memos = previewRetrospection.memos,
            onClickUpdateMemo = { _, _ -> },
            onClickDeleteMemo = {},
            onClickCreateMemo = {},
            onClickClose = {},
            onDismissRequest = {}
        )
    }
}