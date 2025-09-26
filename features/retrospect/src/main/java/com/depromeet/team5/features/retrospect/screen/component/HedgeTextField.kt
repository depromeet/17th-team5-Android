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
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
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
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(1.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.5.dp,
                color = if (isFocused) {
                    HedgeColor.Brand.Darken
                } else {
                    HedgeColor.WHITE
                },
                shape = RoundedCornerShape(16.dp)
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.hasFocus
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
                textStyle = HedgeTypography.Body1.SemiBold,
                visualTransformation = visualTransformation,
                label = {
                    Text(
                        modifier = Modifier.padding(bottom = if (isFocused) 4.dp else 0.dp),
                        text = label,
                        style = HedgeTypography.Label2.SemiBold
                    )
                },
                trailingIcon = trailingIcon,
                placeholder = {
                    Text(
                        text = placeholder,
                        style = HedgeTypography.Body1.Medium
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,

                    focusedLabelColor = HedgeColor.Brand.Darken,
                    unfocusedLabelColor = HedgeColor.Text.Assistive,

                    focusedPlaceholderColor = HedgeColor.Text.Assistive,

                    focusedTextColor = HedgeColor.Text.Title,

                    cursorColor = HedgeColor.Brand.Darken,
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
    readOnly: Boolean = false
) {

    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(1.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.5.dp,
                color = if (isError) {
                    HedgeColor.Feedback.Error
                } else if (isFocused) {
                    HedgeColor.Brand.Darken
                } else {
                    HedgeColor.WHITE
                },
                shape = RoundedCornerShape(16.dp)
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
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
                textStyle = HedgeTypography.Body1.SemiBold,
                visualTransformation = visualTransformation,
                label = {
                    Text(
                        modifier = Modifier.padding(bottom = if (isFocused) 4.dp else 0.dp),
                        text = label,
                        style = HedgeTypography.Label2.SemiBold
                    )
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        style = HedgeTypography.Body1.Medium
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,

                    errorLabelColor = HedgeColor.Feedback.Error,
                    errorTextColor = colorResource(R.color.gray900),
                    errorContainerColor = Color.Transparent,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,

                    focusedLabelColor = HedgeColor.Brand.Darken,
                    unfocusedLabelColor = HedgeColor.Text.Assistive,

                    focusedPlaceholderColor = HedgeColor.Text.Assistive,

                    focusedTextColor = HedgeColor.Text.Title,

                    cursorColor = HedgeColor.Brand.Darken,
                    errorCursorColor = Color.Transparent,
                ),
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                isError = isError,
                readOnly = readOnly
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