package com.depromeet.team5.features.feedback.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.model.Principle
import com.depromeet.team5.features.feedback.AiFeedbackState
import com.depromeet.team5.features.feedback.R
import com.depromeet.team5.features.feedback.component.HedgeToast
import com.depromeet.team5.features.feedback.component.rememberHedgeToastState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun FeedbackRoute(
    modifier: Modifier = Modifier,
    onRemoveClick: () -> Unit,
    viewModel: AiFeedbackViewModel = hiltViewModel()
) {
    val state by viewModel.feedbackStateFlow.collectAsStateWithLifecycle()
    val companyName by viewModel.companyName.collectAsStateWithLifecycle()
    val price by viewModel.price.collectAsStateWithLifecycle()
    val stock by viewModel.stock.collectAsStateWithLifecycle()
    val date by viewModel.date.collectAsStateWithLifecycle()

    val toastState = rememberHedgeToastState()

    FeedbackScreen(
        state = state,
        companyName = companyName,
        price = price,
        stock = stock,
        date = date,
        onRetryClick = {},
        onRemoveClick = onRemoveClick,
        onAddClick = {
            toastState.show()
        }
    )

    if (toastState.isShow) {
        HedgeToast(
            modifier = Modifier.padding(top = 8.dp),
            state = toastState,
            message = {
                Text(
                    text = "내 투자 원칙에 추가되었습니닽",
                    style = HedgeTypography.Body3.Medium,
                    color = HedgeColor.Neutral.BackgroundSecondary
                )
            },
            icon = {
                Image(imageVector = HedgeIcon.Check, contentDescription = null)
            }
        )
    }
}

@Composable
private fun FeedbackScreen(
    state: AiFeedbackState,
    companyName: String,
    price: Long,
    stock: Int,
    date: String,
    onRemoveClick: () -> Unit,
    onRetryClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        stringResource(id = R.string.feedback_my_retrospect),
        stringResource(id = R.string.feedback_ai_feedback)
    )
    val lazyColumnState = rememberLazyListState()
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { tabs.size }
    )

    val scope = rememberCoroutineScope()

    if (state is AiFeedbackState.Failure || state is AiFeedbackState.Error) {
        FeedbackError(onRetryClick = onRetryClick)
        return
    }

    Column(
        modifier = Modifier
            .background(HedgeColor.WHITE)
    ) {
        HedgeTopBar(
            onClickBack = {},
            back = {},
            action = {
                Text(
                    modifier = Modifier
                        .clickable(onClick = onRemoveClick),
                    text = stringResource(id = R.string.feedback_delete),
                    style = HedgeTypography.Body1.SemiBold,
                    color = HedgeColor.Text.Alternative
                )
            }
        )
        Row(
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp, end = 20.dp)
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
                text = companyName,
                style = HedgeTypography.Body3.Medium,
                color = HedgeColor.Text.Title
            )
        }
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 4.dp),
            text = stringResource(R.string.price_and_stock, price, stock),
            style = HedgeTypography.Headline1.SemiBold
        )
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 4.dp),
            text = date,
            style = HedgeTypography.Label2.Regular,
            color = HedgeColor.Text.Alternative
        )

        Spacer(modifier = Modifier.padding(10.dp))

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(HedgeColor.WHITE),
            state = lazyColumnState
        ) {
            item {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = HedgeColor.WHITE)
                        .padding(top = 8.dp),
                    painter = painterResource(R.drawable.img_chart),
                    contentDescription = null
                )
            }
            stickyHeader {
                Box(
                    modifier = Modifier.background(HedgeColor.WHITE)
                ) {
                    TabRow(
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
                        AiFeedbackPage(
                            state = state,
                            onAddClick = onAddClick
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillParentMaxHeight(0.5f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.feedback_not_yet),
                                style = HedgeTypography.Body3.Medium,
                                color = HedgeColor.Text.Secondary
                            )
                        }
                    }
                }
            }
        }
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
private fun AiFeedbackPage(
    state: AiFeedbackState,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        when (state) {
            AiFeedbackState.Loading -> {
                AiLoadingProgress()
            }

            is AiFeedbackState.Success -> {
                Column {
                    AiNotice()
                    Spacer(modifier = Modifier.padding(top = 22.dp))
                    AIContent(
                        state = state,
                        onAddClick = onAddClick,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }

            else -> {}
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
                text = stringResource(id = R.string.feedback_ai_notice),
                style = HedgeTypography.Label2.Medium,
                color = HedgeColor.Feedback.AI
            )
        }
    }
}

@Composable
private fun AIContent(
    state: AiFeedbackState.Success,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE)
            .padding(bottom = 62.dp)
    ) {
        AiHeader(
            icon = {
                Image(painter = painterResource(R.drawable.img_star), contentDescription = "")
            },
            title = {
                Text(
                    text = stringResource(id = R.string.feedback_summary),
                    style = HedgeTypography.Body2.SemiBold,
                    color = HedgeColor.Feedback.AI
                )
            }
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = R.string.feedback_summary_one_line),
            style = HedgeTypography.Headline2.SemiBold,
            color = HedgeColor.Text.Title
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = state.summarize,
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
                    text = stringResource(id = R.string.feedback_market_status),
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
                text = state.summarizeOfMarket,
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
                    text = stringResource(id = R.string.feedback_ai_recommended_principle),
                    style = HedgeTypography.Headline2.SemiBold,
                    color = HedgeColor.Text.Title
                )
            }
        )

        state.principles.forEachIndexed { index, principle ->
            val topPadding = if (index == 0) 16.dp else 22.dp

            PrincipleViewHolder(
                title = principle.title,
                content = principle.content,
                contentPadding = PaddingValues(top = topPadding, bottom = 22.dp),
                onAddClick = onAddClick
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = HedgeColor.Neutral.BackgroundSecondary
            )
        }
    }
}

@Composable
private fun PrincipleViewHolder(
    title: String,
    content: String,
    onAddClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(contentPadding)
    ) {
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
                onClick = onAddClick,
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
                        text = stringResource(id = R.string.feedback_add),
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
                text = stringResource(id = R.string.feedback_ai_feedback_in_progress),
                style = HedgeTypography.Body3.Medium,
                color = HedgeColor.Feedback.AI
            )
        }
    }
}

@Composable
private fun FeedbackError(
    onRetryClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.feedback_error),
            style = HedgeTypography.Body3.Medium,
            color = HedgeColor.Text.Secondary,
            textAlign = TextAlign.Center
        )
        //todo 추후에 Retry 관련 버튼 만들기
    }
}

@Composable
@Preview
fun FeedbackErrorPreview() {
    FeedbackError()
}

@Suppress("UnusedPrivateMember")
@Composable
@Preview
private fun AiNoticePreview() {
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
                    text = stringResource(id = R.string.feedback_summary),
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
            state = AiFeedbackState.Success(
                summarize = "사실 몇 줄까지 나올지 모르겠음 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? ",
                summarizeOfMarket = "최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 ",
                principles = listOf(
                    Principle(
                        title = "원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까",
                        content = "최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄"
                    ),
                    Principle(
                        title = "원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까",
                        content = "최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄"
                    ),
                    Principle(
                        title = "원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까",
                        content = "최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄"
                    )
                )
            ),
            modifier = Modifier.padding(horizontal = 20.dp),
            onAddClick = {}
        )
    }
}

@Suppress("UnusedPrivateMember")
@Composable
@Preview
fun AiFeedBackScreenPreview() {
    var state: AiFeedbackState by remember { mutableStateOf(AiFeedbackState.Loading) }
    val toastState = rememberHedgeToastState()

    LaunchedEffect(Unit) {

        delay(4000)
        state = AiFeedbackState.Success(
            summarize = "사실 몇 줄까지 나올지 모르겠음 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? 최대 4~5줄 정도가 좋지 않을까? ",
            summarizeOfMarket = "최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 설명 최대 3줄까지 ",
            principles = listOf(
                Principle(
                    title = "원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까",
                    content = "최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄"
                ),
                Principle(
                    title = "원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까",
                    content = "최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄"
                ),
                Principle(
                    title = "원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까지 원칙 타이틀 최대 2줄까",
                    content = "최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄까지 최대 3줄"
                )
            )
        )
    }

    FeedbackScreen(
        companyName = "삼성전자",
        price = 65000,
        stock = 3,
        date = "2025년 8월 25일",
        state = state,
        onRemoveClick = {},
        onRetryClick = {},
        onAddClick = {
            toastState.show()
        }
    )

    if (toastState.isShow) {
        HedgeToast(
            modifier = Modifier.padding(top = 8.dp),
            state = toastState,
            message = {
                Text(
                    text = "회고가 삭제되었습니다",
                    style = HedgeTypography.Body3.Medium,
                    color = HedgeColor.Neutral.BackgroundSecondary
                )
            },
            icon = {
                Image(imageVector = HedgeIcon.Check, contentDescription = null)
            }
        )
    }
}
