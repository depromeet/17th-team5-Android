package com.depromeet.team5.features.feedback.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.features.feedback.AiFeedbackState
import com.depromeet.team5.features.feedback.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun FeedbackRoute(
    modifier: Modifier,
    viewModel: AiFeedbackViewModel = hiltViewModel()
) {

    FeedbackScreen(
        state = AiFeedbackState.Loading
    )
}

@Composable
private fun FeedbackScreen(
    modifier: Modifier = Modifier,
    state: AiFeedbackState,
    onClickBackPressed: () -> Unit = {},
) {
    val tabs = listOf("나의 회고", "AI 피드백")
    val lazyColumnState = rememberLazyListState()
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { tabs.size }
    )

    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE),
        state = lazyColumnState
    ) {
        item {
            Column(
                modifier = Modifier
                    .background(color = HedgeColor.WHITE)
            ) {
                HedgeTopBar(
                    onClickBack = onClickBackPressed,
                    action = {
                        Text(
                            text = "삭제",
                            style = HedgeTypography.Body1.SemiBold,
                            color = HedgeColor.Text.Alternative
                        )
                    }
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(20.dp))
                    )

                    Text(
                        modifier = Modifier.padding(start = 7.dp),
                        text = "삼성전자",
                        style = HedgeTypography.Body3.Medium,
                        color = HedgeColor.Text.Title
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp),
                    text = "65,000원・3주 매도",
                    style = HedgeTypography.Headline1.SemiBold
                )
                Text(
                    modifier = Modifier.padding(start = 20.dp, top = 4.dp),
                    text = "65,000원・3주 매도",
                    style = HedgeTypography.Label2.Regular,
                    color = HedgeColor.Text.Alternative
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = HedgeColor.WHITE)
                        .padding(top = 13.dp),
                    painter = painterResource(R.drawable.img_chart),
                    contentDescription = null
                )
            }
        }
        stickyHeader {
            Box(
                modifier = Modifier.background(HedgeColor.WHITE)
            ) {
                TabRow(
                    modifier = Modifier.padding(top = 10.dp),
                    containerColor = HedgeColor.WHITE,
                    contentColor = HedgeColor.Text.Primary,
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            height = 2.dp,
                            color = HedgeColor.Text.Title
                        )
                    },
                    divider = {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = HedgeColor.Neutral.BackgroundSecondary
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    text = tabs[index]
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            selectedContentColor = HedgeColor.Text.Primary,
                            unselectedContentColor = HedgeColor.Text.Alternative
                        )
                    }
                }
            }
        }

        item {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                if (page == 1) {
                    AiFeedbackPage(state = state)
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            "$page",
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AiFeedbackPage(
    modifier: Modifier = Modifier,
    state: AiFeedbackState
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (state) {
            is AiFeedbackState.Success -> {
                Column {
                    AiNotice()
                    Spacer(modifier = Modifier.padding(top = 22.dp))
                    AIContent(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }

            is AiFeedbackState.Failure,
            AiFeedbackState.Loading -> {
                AiLoadingProgress()
            }
        }
    }
}

@Composable
private fun AiNotice() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = HedgeColor.Feedback.AI.copy(alpha = 0.05f)
            )
            .padding(start = 20.dp, end = 20.dp, bottom = 22.dp),
    ) {
        Row {
            Image(
                modifier =
                    Modifier
                        .padding(top = 24.dp)
                        .size(16.dp),
                imageVector = HedgeIcon.Empty,
                contentDescription = "",
                colorFilter = ColorFilter.tint(HedgeColor.Feedback.AI.copy(alpha = 0.9f))
            )

            Text(
                modifier = Modifier.padding(top = 22.dp, start = 10.dp),
                text = "본 서비스에서 제공하는 AI 피드백은 투자 참고용 정보이며, 실제 투자 판단에 대한 책임은 사용자 본인에게 있습니다.",
                style = HedgeTypography.Label2.Medium,
                color = HedgeColor.Feedback.AI
            )
        }
    }
}

@Composable
private fun AIContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE)
            .padding(bottom = 44.dp)
    ) {
        AiHeader(
            icon = {
                Image(painter = painterResource(R.drawable.img_star), contentDescription = "")
            },
            title = {
                Text(
                    text = "요약",
                    style = HedgeTypography.Body2.SemiBold,
                    color = HedgeColor.Feedback.AI
                )
            }
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "요약 한 마디",
            style = HedgeTypography.Headline2.SemiBold,
            color = HedgeColor.Text.Title
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "사실 몇 줄까지 나올지 모르겠음 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까?",
            style = HedgeTypography.Body3.Medium,
            color = HedgeColor.Text.Secondary,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
        AiHeader(
            modifier = Modifier.padding(top = 44.dp),
            icon = {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(color = HedgeColor.Text.Disabled)
                )
            },
            title = {
                Text(
                    text = "당시 시장 현황",
                    style = HedgeTypography.Headline2.SemiBold,
                    color = HedgeColor.Text.Title
                )
            }
        )
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(HedgeColor.Neutral.BackgroundSecondary)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 32.dp),
                text = "최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지",
                style = HedgeTypography.Body3.Medium,
                color = HedgeColor.Text.Secondary,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        AiHeader(
            modifier = Modifier.padding(top = 44.dp),
            icon = {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(color = HedgeColor.Text.Disabled)
                )
            },
            title = {
                Text(
                    text = "AI 추천 원칙",
                    style = HedgeTypography.Headline2.SemiBold,
                    color = HedgeColor.Text.Title
                )
            }
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        PrincipleViewHolder(
            title = "원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까",
            content = "원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최"
        ) { }
        PrincipleViewHolder(
            title = "원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까",
            content = "원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최"
        ) { }
        PrincipleViewHolder(
            title = "원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까",
            content = "원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최대 3줄까지 원칙 내용 최"
        ) { }
    }
}

@Composable
private fun AiHeader(
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.padding(start = 8.dp))
        title()
    }
}

@Composable
private fun PrincipleViewHolder(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onClickedAddButton: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = HedgeTypography.Body2.SemiBold,
                color = HedgeColor.Text.Title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Button(
                modifier = Modifier
                    .padding(start = 40.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = onClickedAddButton,
                colors = ButtonDefaults.buttonColors(
                    containerColor = HedgeColor.Brand.Primary,
                    contentColor = HedgeColor.WHITE
                ),
                contentPadding = PaddingValues(start = 8.dp, top = 8.dp, end = 10.dp, bottom = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = painterResource(R.drawable.img_add), contentDescription = "")
                    Text(
                        modifier = Modifier.padding(start = 3.2.dp),
                        text = "추가",
                        style = HedgeTypography.Label2.SemiBold,
                        color = HedgeColor.Neutral.BackgroundDefault
                    )
                }
            }
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = content,
            style = HedgeTypography.Label1.Regular
        )
    }
}

@Composable
private fun AiLoadingProgress(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = HedgeColor.Feedback.AI.copy(alpha = 0.05f)
            )
            .padding(start = 20.dp, end = 20.dp, bottom = 22.dp),
    ) {
        Row(
            modifier = modifier.padding(top = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.5.dp,
                color = HedgeColor.Feedback.AI,
                strokeCap = StrokeCap.Round,
                trackColor = HedgeColor.Feedback.AI.copy(alpha = 0.1f),
            )
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = "AI 피드백 작성중...",
                style = HedgeTypography.Body3.Medium,
                color = HedgeColor.Feedback.AI
            )
        }
    }
}


@Suppress("UnusedPrivateMember")
@Composable
@Preview
fun AiNoticePreview() {
    Box(
        modifier = Modifier
            .background(HedgeColor.WHITE),
        contentAlignment = Alignment.Center
    ) {
        AiNotice()
    }
}

@Suppress("UnusedPrivateMember")
@Composable
@Preview
fun AiHeaderPreview() {
    Box(
        modifier = Modifier
            .background(HedgeColor.WHITE),
        contentAlignment = Alignment.Center
    ) {
        AiHeader(
            icon = {
                Image(painter = painterResource(R.drawable.img_star), contentDescription = "")
            },
            title = {
                Text(
                    text = "요약",
                    style = HedgeTypography.Body2.SemiBold,
                    color = HedgeColor.Feedback.AI
                )
            }
        )
    }
}

@Suppress("UnusedPrivateMember")
@Composable
@Preview
fun AiLoadingProgressPreview() {
    Box(
        modifier = Modifier
            .background(HedgeColor.WHITE),
        contentAlignment = Alignment.Center
    ) {
        AiLoadingProgress()
    }
}

@Suppress("UnusedPrivateMember")
@Composable
@Preview
fun AiContentPreview() {
    Box(
        modifier = Modifier
            .background(HedgeColor.WHITE)
            .wrapContentSize()
    ) {
        AIContent(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
    }
}

@Suppress("UnusedPrivateMember")
@Composable
@Preview
fun AiFeedBackScreenPreview() {
    var state: AiFeedbackState by remember { mutableStateOf(AiFeedbackState.Loading) }

    LaunchedEffect(Unit) {

        delay(4000)
        state = AiFeedbackState.Success("")
    }

    Scaffold { contentPadding ->
        FeedbackScreen(
            modifier = Modifier.padding(contentPadding),
            state = state
        )
    }
}
