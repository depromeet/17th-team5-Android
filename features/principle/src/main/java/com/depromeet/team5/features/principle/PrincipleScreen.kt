//package com.depromeet.team5.features.principle
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.depromeet.team5.core.designsystem.component.HedgeButton
//import com.depromeet.team5.core.designsystem.component.HedgeTopBar
//import com.depromeet.team5.core.designsystem.foundation.HedgeColor
//import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
//import com.depromeet.team5.core.navigation.request.PrincipleCheckParams
//import com.depromeet.team5.core.navigation.request.RequestViewModel
//
//data class Principle(
//    val id: Long,
//    val description: String,
//    val checked: Boolean = false,
//    val icon: ImageVector? = null,
//)
//
//@Composable
//fun PrincipleRoute(
//    onBackPressed: () -> Unit,
//    modifier: Modifier = Modifier,
//    onClickNext: () -> Unit,
//    requestViewModel: RequestViewModel = hiltViewModel(),
//    viewModel: PrincipleViewModel = hiltViewModel(),
//) {
//    val principles by viewModel.principles.collectAsStateWithLifecycle()
//    val hasAnyChecked by remember(principles) {
//        derivedStateOf { principles.any(Principle::checked) }
//    }
//    val orderType = requestViewModel.request.orderType
//
//    val handleNext = remember(principles) {
//        {
//            val selected = principles
//                .filter { it.checked }
//                .map { p ->
//                    PrincipleCheckParams(
//                        isFollowed = true,
//                        principleId = p.id.toInt()
//                    )
//                }
//
//            requestViewModel.request =
//                requestViewModel.request.copy(principleChecks = selected)
//
//            onClickNext()
//        }
//    }
//
//    PrincipleScreen(
//        orderType = orderType.toKorean(),
//        onBackPressed = onBackPressed,
//        onClickNext = handleNext,
//        principles = principles,
//        hasAnyChecked = hasAnyChecked,
//        onClickPrinciple = { id -> viewModel.toggle(id) },
//        modifier = modifier,
//    )
//}
//
//@Composable
//private fun PrincipleScreen(
//    orderType: String,
//    onBackPressed: () -> Unit,
//    onClickNext: () -> Unit,
//    principles: List<Principle>,
//    hasAnyChecked: Boolean,
//    onClickPrinciple: (Long) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    Scaffold(
//        topBar = {
//            HedgeTopBar(
//                onClickBack = onBackPressed,
//                action = {
//                    HedgeButton.Text(
//                        text = stringResource(R.string.principle_skip),
//                        imageVector = null,
//                        size = HedgeButton.Text.Size.Medium,
//                        color = HedgeButton.Text.Color.Secondary,
//                        onClick = onClickNext
//                    )
//                }
//            )
//        },
//        bottomBar = {
//            HedgeButton.Action.Filled(
//                text = stringResource(R.string.principle_next_button),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .navigationBarsPadding()
//                    .padding(horizontal = 20.dp, vertical = 10.dp),
//                buttonColors = HedgeButton.Action.Color.Filled.Primary,
//                enabled = hasAnyChecked,
//                onClick = onClickNext
//            )
//        },
//        containerColor = HedgeColor.Neutral.BackgroundDefault
//    ) { innerPadding ->
//        Column(
//            modifier = modifier.padding(innerPadding)
//        ) {
//            Text(
//                text = if (hasAnyChecked)
//                    stringResource(
//                        R.string.principle_checked_title,
//                        principles.count { it.checked },
//                        orderType
//                    )
//                else stringResource(R.string.principle_title, orderType),
//                color = HedgeColor.Text.Title,
//                style = HedgeTypography.Headline1.SemiBold,
//                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
//            )
//            Spacer(Modifier.size(8.dp))
//
//            LazyColumn {
//                items(
//                    items = principles,
//                    key = { item -> item.id }
//                ) { item ->
//                    PrincipleItem(
//                        principle = item,
//                        onClickItem = { onClickPrinciple(item.id) }
//                    )
//                    HorizontalDivider(
//                        color = HedgeColor.Neutral.BackgroundSecondary,
//                        thickness = 1.dp
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun PrincipleItem(
//    principle: Principle,
//    onClickItem: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    Row(
//        modifier = modifier
//            .clickable(
//                onClick = onClickItem,
//                interactionSource = remember { MutableInteractionSource() },
//            )
//            .padding(horizontal = 24.dp, vertical = 22.dp),
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        Icon(
//            painter = painterResource(R.drawable.ic_sample_principle),
//            contentDescription = null,
//            tint = Color.Unspecified,
//            modifier = Modifier.size(32.dp)
//        )
//
//        Spacer(Modifier.size(16.dp))
//
//        Text(
//            text = principle.description,
//            modifier = Modifier.weight(1f),
//            style = HedgeTypography.Body3.SemiBold,
//            color = HedgeColor.GREY_900
//        )
//
//        Spacer(Modifier.size(24.dp))
//
//        Box(
//            modifier = Modifier
//                .size(26.dp)
//                .clip(RoundedCornerShape(8.dp))
//                .background(
//                    color = HedgeColor.Neutral.BackgroundSecondary,
//                )
//        ) {
//            if (principle.checked) {
//                Image(
//                    painter = painterResource(R.drawable.ic_check),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .matchParentSize()
//                        .align(Alignment.Center)
//                        .background(color = HedgeColor.Brand.Primary)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//@Preview
//private fun PrincipleScreenPreview() {
//    val items = remember {
//        mutableStateListOf(
//            Principle(1, "안전마진을 확보하라1"),
//            Principle(2, "안전마진을 확보하라2"),
//            Principle(3, "안전마진을 확보하라3"),
//            Principle(4, "안전마진을 확보하라4"),
//            Principle(5, "안전마진을 확보하라5"),
//            Principle(6, "안전마진을 확보하라6"),
//            Principle(7, "안전마진을 확보하라7"),
//            Principle(8, "안전마진을 확보하라8"),
//            Principle(9, "안전마진을 확보하라9"),
//            Principle(10, "안전마진을 확보하라10"),
//        )
//    }
//    val hasAnyChecked by remember {
//        derivedStateOf { items.any { it.checked } }
//    }
//
//    PrincipleScreen(
//        orderType = "",
//        onBackPressed = {},
//        onClickNext = {},
//        principles = items,
//        hasAnyChecked = hasAnyChecked,
//        onClickPrinciple = { id ->
//            val idx = items.indexOfFirst { it.id == id }
//            if (idx != -1) {
//                items[idx] = items[idx].copy(checked = !items[idx].checked)
//            }
//        }
//    )
//}
