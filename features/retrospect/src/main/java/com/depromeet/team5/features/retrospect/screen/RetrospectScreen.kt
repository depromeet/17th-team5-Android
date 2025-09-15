package com.depromeet.team5.features.retrospect.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.team5.features.retrospect.R
import com.depromeet.team5.features.retrospect.screen.annotation.CurrencyUnit
import com.depromeet.team5.features.retrospect.screen.annotation.KOREAN
import com.depromeet.team5.features.retrospect.screen.annotation.PERCENT


@Composable
fun RetrospectRoute(
    modifier: Modifier,
    onBackPressed: () -> Unit
) {

    RetrospectScreen(modifier, onBackPressed)
}

@Composable
private fun RetrospectScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    var returnVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F4F6))
    ) {
        HedgeTopbar { }
        CompanyTitle(
            modifier = Modifier.padding(start = 16.dp, top = 10.dp)
        )

        Text(
            modifier = modifier.padding(start = 16.dp, top = 8.dp),
            text = "얼마에 매도하셨나요?",
            fontWeight = FontWeight.W600,
            fontSize = 22.sp,
            color = Color(0xFF111827)
        )

        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 26.dp, end = 20.dp)
                .wrapContentSize()
                .background(
                    color = colorResource(R.color.white),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(1.dp, colorResource(R.color.white), RoundedCornerShape(16.dp))
        ) {
            HedgeTextField(
                label = "매도가",
                placeholder = "1주당 가격",
                unit = KOREAN
            )
            HedgeTextField(
                label = "거래량",
                placeholder = "거래량",
                unit = KOREAN
            )
            HedgeTextField(
                label = "거래 날짜",
                placeholder = "거래 날짜"
            )
        }

        if (returnVisibility) {
            HedgeTextField(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 12.dp),
                label = "수익률",
                placeholder = "%",
                unit = PERCENT
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "수익률도 입력하기",
                        color = colorResource(R.color.gray700),
                        fontSize = 15.sp,
                        letterSpacing = 0.14.sp,
                        fontWeight = FontWeight.W600
                    )
                    Text(
                        text = "더 자세한 AI 분석이 가능해요",
                        color = colorResource(R.color.alternative),
                        fontSize = 13.sp,
                        letterSpacing = 0.03.sp,
                        fontWeight = FontWeight.W600
                    )
                }

                Switch(
                    checked = returnVisibility,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(R.color.white),
                        uncheckedThumbColor = colorResource(R.color.white),
                        checkedTrackColor = colorResource(R.color.gray300),
                        uncheckedTrackColor = colorResource(R.color.gray300)
                    ),
                    onCheckedChange = {
                        returnVisibility = it
                    }
                )
            }

            TextButton(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(57.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primary)
                ),
                onClick = {}
            ) {
                Text(
                    text = "확인",
                    color = colorResource(R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}

@Composable
private fun HedgeTopbar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(color = Color(0xFFF3F4F6)),
        contentAlignment = Alignment.CenterStart
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(start = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(R.drawable.icon_arrow_left),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun CompanyTitle(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color.Gray, shape = RoundedCornerShape(20.dp))
        )

        Text(
            modifier = Modifier.padding(start = 7.dp),
            text = "삼성전자"
        )

    }
}

@Composable
private fun HedgeTextField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    unit: CurrencyUnit? = null
) {
    var borderColor by remember { mutableIntStateOf(R.color.brand500) }
    var inputText by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .padding(1.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.5.dp, colorResource(borderColor), RoundedCornerShape(16.dp))
            .onFocusChanged { focusState ->
                borderColor = if (focusState.hasFocus) R.color.gray900
                else R.color.white

            }
            .padding(vertical = 14.dp, horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 4.dp),
                value = inputText,
                onValueChange = { inputText = it },
                visualTransformation = if (unit != null) {
                    CurrencyVisualTransformation(unit)
                } else {
                    VisualTransformation.None
                },
                label = {
                    Text(
                        text = label,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                },
                placeholder = { Text(placeholder) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,

                    focusedLabelColor = colorResource(R.color.gray900),
                    unfocusedLabelColor = colorResource(R.color.gray500),

                    focusedPlaceholderColor = colorResource(R.color.gray400),

                    focusedTextColor = colorResource(R.color.gray900),

                    cursorColor = Color.Transparent,
                    errorCursorColor = Color.Transparent,
                )
            )
        }
    }
}


@Preview
@Composable
private fun RetrospectScreenPreview() {
    HedgeTopbar(
        onBackPressed = {}
    )
}

@Preview
@Composable
private fun CompanyTitlePreview() {
    CompanyTitle()
}

@Preview
@Composable
private fun HedgeTextFieldPreview() {
    HedgeTextField(
        label = "매도가",
        placeholder = "매도 가격"
    )

}


@Preview
@Composable
private fun RetrospectRoutePreview() {
    RetrospectRoute(
        modifier = Modifier,
        onBackPressed = {}
    )
}

