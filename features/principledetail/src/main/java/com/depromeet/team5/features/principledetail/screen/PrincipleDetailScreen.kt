package com.depromeet.team5.features.principledetail.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.features.principledetail.R


@Composable
fun PrincipleDetailRoute(
    modifier: Modifier = Modifier
) {


}

@Composable
private fun PrincipleDetailScreen(
    modifier: Modifier = Modifier
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
                onBackPressed = {},
                onClickedModifyButton = {},
                onClickedRemoveButton = {}
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
                        .background(HedgeColor.WHITE)
                ) {
                    //todo Image인지 유니코드인지 구분해서 사용하기
//                if(thumbnail == UNICODE) {
//                    Text(
//                        text = unicode
//                    )
//                } else {
//                    Image()
//                }
                }

                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 30.dp),
                    text = "이건 좀 지키자 제발",
                    style = HedgeTypography.Headline1.SemiBold,
                    color = HedgeColor.Text.Title
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            ) {
                items(
                    items = mutableListOf(
                        "1",
                        "2",
                        "3",
                        "4",
                        "5",
                        "6",
                        "7",
                        "8",
                        "9",
                        "10",
                        "11",
                        "12",
                        "13"
                    ),
                    key = { it }
                ) {
                    //todo MyPrinciple 타입 사용하기
                    PrincipleItem(
                        index = 1,
                        title = "종목 선택 시 최근 매출액 확인하기 종목 선택 시 최근 매출액 확인하기",
                        content = "상승장에서 눌림목 나오면 지지선 나올 때까지 기다렸다가 분할 매수하자. 몰빵은 절대 금지."
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = HedgeColor.Neutral.BackgroundSecondary
                    )
                }
            }
        }

        //todo 추천 원칙인지 내 원칙인지 구분에 따라 버튼 구분하기
        PrincipleDetailConfirmButton(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PrincipleItem(
    index: Int,
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    var isExpand by remember { mutableStateOf(false) }

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
                text = title,
                style = HedgeTypography.Body2.SemiBold,
                color = HedgeColor.Text.Primary
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = content,
                style = HedgeTypography.Body3.Regular,
                color = HedgeColor.Text.Primary
            )
        }

        Box {
            Image(
                modifier = Modifier.padding(start = 10.dp),
                imageVector = HedgeIcon.Menu,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = HedgeColor.Text.Disabled)
            )

            ModifyAndRemoveDropdown(
                expand = isExpand,
                onClickedModifyButton = {},
                onClickedRemoveButton = {},
                onDismissRequest = { isExpand = false }
            )
        }
    }
}

@Composable
fun PrincipleDetailConfirmButton(
    modifier: Modifier = Modifier
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
                    onClick = {}
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
    onClickedRemoveButton: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
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
                    .clickable(enabled = true) {
                        isExpanded = !isExpanded
                    },
                imageVector = HedgeIcon.Menu,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = HedgeColor.Text.Primary)
            )
            if (isExpanded) {
                ModifyAndRemoveDropdown(
                    expand = isExpanded,
                    onClickedModifyButton = onClickedModifyButton,
                    onClickedRemoveButton = onClickedRemoveButton,
                    onDismissRequest = { isExpanded = false }
                )
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


@Preview
@Composable
fun PrincipleDetailScreenPreview() {
    PrincipleDetailScreen()
}


@Preview(showBackground = true)
@Composable
fun TopbarPreview() {
    Topbar(
        modifier = Modifier
            .background(HedgeColor.Brand.Secondary),
        onBackPressed = {},
        onClickedModifyButton = {},
        onClickedRemoveButton = {}
    )
}

@Preview
@Composable
fun PrincipleItemPreview() {
    PrincipleItem(
        index = 1,
        title = "종목 선택 시 최근 매출액 확인하기 종목 선택 시 최근 매출액 확인하기 ",
        content = "상승장에서 눌림목 나오면 지지선 나올 때까지 기다렸다가 분할 매수하자. 몰빵은 절대 금지."
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
        PrincipleDetailConfirmButton()
    }
}