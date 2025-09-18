package com.depromeet.team5.features.retrospect.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.team5.features.retrospect.R
import com.depromeet.team5.features.retrospect.screen.component.HedgeSimpleTextField
import com.depromeet.team5.features.retrospect.screen.component.HedgeSwitch
import com.depromeet.team5.features.retrospect.screen.component.HedgeTopbar
import com.depromeet.team5.features.retrospect.screen.component.HedgeUnitTextField
import com.depromeet.team5.features.retrospect.screen.visualtransmation.DateVisualTransformation
import com.depromeet.team5.features.retrospect.screen.visualtransmation.KoreanCurrencyVisualTransformation
import com.depromeet.team5.features.retrospect.screen.visualtransmation.USDCurrencyVisualTransformation
import com.depromeet.team5.features.retrospect.screen.visualtransmation.UnitTransformation
import kotlinx.coroutines.delay


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
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(returnVisibility) {
        if (returnVisibility) {
            delay(300)
            focusRequester.requestFocus()
        }
    }

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
            CurrencyTextField()
            SimpleNumberTextField(
                label = "거래량",
                placeholder = "거래량 가격",
                visualTransformation = UnitTransformation("주")
            )
            DateTextField()
        }

        AnimatedVisibility(
            visible = returnVisibility,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 300,
                    delayMillis = 0,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 200,
                    delayMillis = 0,
                    easing = FastOutSlowInEasing
                )
            )
        ) {
            SimpleNumberTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp),
                label = "수익률",
                placeholder = "%",
                visualTransformation = UnitTransformation("%")
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
private fun CurrencyTextField() {
    val keyboardController = LocalSoftwareKeyboardController.current

    var isToggled by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val currentVisualTransformation =
        if (isToggled) USDCurrencyVisualTransformation() else KoreanCurrencyVisualTransformation()

    HedgeUnitTextField(
        label = "매도가",
        placeholder = "매도 가격",
        value = textFieldValue,
        onValueChange = { newValue ->
            val digitsOnlyText = newValue.text.filter { it.isDigit() }
            val newCursorPosition = newValue.selection.start.let { transformedOffset ->
                newValue.text.substring(0, transformedOffset).count { it.isDigit() }
            }.coerceIn(0, digitsOnlyText.length)

            textFieldValue = TextFieldValue(
                text = digitsOnlyText,
                selection = androidx.compose.ui.text.TextRange(newCursorPosition)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        trailingIcon = {
            CurrencySwitch(
                isToggled = isToggled,
                onToggleChanged = { newToggleState ->
                    isToggled = newToggleState
                    textFieldValue = TextFieldValue("")
                }
            )
        },
        visualTransformation = currentVisualTransformation
    )

}

@Composable
private fun SimpleNumberTextField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    HedgeSimpleTextField(
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        visualTransformation = visualTransformation
    )
}

@Composable
private fun DateTextField() {
    val keyboardController = LocalSoftwareKeyboardController.current

    HedgeSimpleTextField(
        label = "거래 날짜",
        placeholder = "거래 날짜",
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        visualTransformation = DateVisualTransformation()
    )
}

@Composable
private fun CurrencySwitch(
    isToggled: Boolean,
    onToggleChanged: (Boolean) -> Unit
) {
    HedgeSwitch(
        isToggled = isToggled,
        onToggleChanged = onToggleChanged,
        offContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 7.dp, vertical = 6.dp),
                    text = "원",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600
                )
            }
        },
        onContent = {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 7.dp, vertical = 6.dp),
                    text = "$",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
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
private fun RetrospectRoutePreview() {
    RetrospectRoute(
        modifier = Modifier,
        onBackPressed = {}
    )
}
