package com.depromeet.team5.features.retrospect.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextRange
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
import com.depromeet.team5.features.retrospect.screen.visualtransmation.KoreanCurrencyVisualTransformation
import com.depromeet.team5.features.retrospect.screen.visualtransmation.USDCurrencyVisualTransformation


@Composable
internal fun HedgeUnitTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    trailingIcon: @Composable () -> Unit = {}
) {
    var borderColor by remember { mutableIntStateOf(R.color.brand500) }

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
            .padding(start = 20.dp, top = 14.dp, end = 16.dp, bottom = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                value = value,
                onValueChange = onValueChange,
                visualTransformation = visualTransformation,
                label = {
                    Text(
                        text = label,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                },
                trailingIcon = trailingIcon,
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
                ),
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                enabled = enabled
            )
        }
    }
}

@Composable
internal fun HedgeSimpleTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    isError: Boolean = false,
) {
    var borderColor by remember(isError) {
        if (isError) {
            mutableIntStateOf(R.color.error)
        } else {
            mutableIntStateOf(R.color.white)
        }
    }

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
                if (isError) return@onFocusChanged

                borderColor = when (focusState.hasFocus) {
                    true -> R.color.gray900
                    else -> R.color.white
                }
            }
            .padding(start = 20.dp, top = 14.dp, end = 16.dp, bottom = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                value = value,
                onValueChange = onValueChange,
                visualTransformation = visualTransformation,
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

                    errorLabelColor = colorResource(R.color.error),
                    errorTextColor = colorResource(R.color.gray900),
                    errorContainerColor = Color.Transparent,

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
                ),
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                isError = isError
            )
        }
    }
}


@Preview
@Composable
private fun HedgeTextFieldPreview() {
    val keyboardController = LocalSoftwareKeyboardController.current

    var isToggled by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.gray200)),
        verticalArrangement = Arrangement.Center
    ) {
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
                    selection = TextRange(newCursorPosition)
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
                HedgeSwitch(
                    isToggled = isToggled,
                    onToggleChanged = { newToggleState ->
                        isToggled = newToggleState
                        textFieldValue = TextFieldValue("")
                    },
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
            },
            visualTransformation = if (isToggled) {
                USDCurrencyVisualTransformation()
            } else {
                KoreanCurrencyVisualTransformation()
            }
        )
    }
}