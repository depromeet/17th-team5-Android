package com.depromeet.team5.features.principlemodification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography


@Composable
fun PrincipleModificationRoute(
    modifier: Modifier = Modifier
) {

}

@Composable
private fun PrincipleModificationScreen(
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable { mutableStateOf("상승장에서 눌림목 나오면 지지선 나올 때까지 기다렸다가 분할 매수하자. 몰빵은 절대 금지다!!!!!") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HedgeColor.WHITE)
    ) {
        Topbar(
            title = ""
        ) { }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            text = "종목 선택 시 최근 매출액 확인!!!!",
            style = HedgeTypography.Headline1.SemiBold,
            color = HedgeColor.Text.Title
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            color = HedgeColor.Neutral.BackgroundSecondary
        )

        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 24.dp),
            value = text,
            onValueChange = {
                text = it
            },
            textStyle = HedgeTypography.Body3.Regular.copy(
                color = HedgeColor.Text.Title
            )
        )
    }
}

@Composable
private fun Topbar(
    title: String,
    modifier: Modifier = Modifier,
    onClickedButton: () -> Unit
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
                .size(24.dp),
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
            imageVector = null,
            size = HedgeButton.Text.Size.Large,
            onClick = onClickedButton
        )
    }
}

@Preview
@Composable
private fun PrincipleModificationScreenPreview(
    modifier: Modifier = Modifier
) {
    PrincipleModificationScreen()
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