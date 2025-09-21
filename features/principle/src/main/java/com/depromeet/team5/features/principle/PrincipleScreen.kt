package com.depromeet.team5.features.principle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography

data class Principle(
    val description: CharSequence,
    val checked: Boolean,
    val icon: ImageVector,
)

@Composable
fun PrincipleRoute(
    modifier: Modifier = Modifier,
    viewModel: RetrospectViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    PrincipleScreen(
        modifier = modifier,
        onBackPressed = {},
        onClickNext = {},
        onClickPrinciple = {},
    )
}

@Composable
private fun PrincipleScreen(
    onBackPressed: () -> Unit,
    onClickNext: () -> Unit,
    onClickPrinciple: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val defaultPrinciples: List<Principle> = listOf(
        Principle("안전마진을 확보하라1", false, HedgeIcon.error),
        Principle("안전마진을 확보하라2", true, HedgeIcon.error),
        Principle("안전마진을 확보하라3", false, HedgeIcon.error),
        Principle("안전마진을 확보하라4", true, HedgeIcon.error),
        Principle("안전마진을 확보하라5", false, HedgeIcon.error),
        Principle("안전마진을 확보하라6", false, HedgeIcon.error),
        Principle("안전마진을 확보하라7", false, HedgeIcon.error),
        Principle("안전마진을 확보하라8", false, HedgeIcon.error),
        Principle("안전마진을 확보하라9", false, HedgeIcon.error),
        Principle("안전마진을 확보하라10", false, HedgeIcon.error),
        Principle("안전마진을 확보하라11", false, HedgeIcon.error),
        Principle("안전마진을 확보하라12", false, HedgeIcon.error),
        Principle("안전마진을 확보하라13", false, HedgeIcon.error),
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = HedgeColor.Neutral.BackgroundDefault)
    ) {
        Column(
            modifier = Modifier,
        ) {
            HedgeTopBar(
                onClickBack = onBackPressed,
                action = {
                    HedgeButton.Text(
                        text = "건너뛰기",
                        imageVector = null,
                        size = HedgeButton.Text.Size.Medium,
                        color = HedgeButton.Text.Color.Primary, //색상 대응 Secondary
                        onClick = onClickNext,
                    )
                }
            )
            Text(
                text = "어떤 투자 원칙에 따른\n{매도}였나요?",
                color = HedgeColor.Text.Title,
                style = HedgeTypography.Headline1.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            )
            Spacer(Modifier.size(8.dp))
            LazyColumn {
                itemsIndexed(
                    items = defaultPrinciples,
                    key = { _, item -> item.hashCode() },
                ) { index, item ->
                    PrincipleItem(
                        principle = item,
                        onClickItem = { onClickPrinciple(index) }
                    )
                }
            }
        }
        HedgeButton.CallToAction.Single(
            text = "다음",
            onClick = onClickNext,
            background = HedgeButton.CallToAction.Background.Gradient(HedgeColor.Neutral.BackgroundDefault),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun PrincipleItem(
    principle: Principle,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            Modifier
                .size(32.dp)
                .drawBehind {
                    drawCircle(
                        color = Color.Black,
                        radius = size.minDimension / 2f,
                        center = center
                    )
                },
        )
        Spacer(Modifier.size(16.dp))
        Text(
            text = principle.description.toString(),
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.size(24.dp))
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    color = HedgeColor.Neutral.BackgroundSecondary,
                )
        ) {
            if (principle.checked) {
                Image(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize()
                        .align(Alignment.Center)
                        .background(color = HedgeColor.Brand.Primary)
                )
            }
        }
    }
}

@Composable
@Preview
private fun PrincipleScreenPreview() {
    PrincipleScreen(
        onBackPressed = {},
        onClickNext = {},
        onClickPrinciple = {},
    )
}
