package com.depromeet.team5.features.retrospect.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depromeet.team5.core.designsystem.component.HedgeSegment
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.features.retrospect.R
import com.depromeet.team5.features.retrospect.annotation.KRW
import com.depromeet.team5.features.retrospect.annotation.USD
import com.depromeet.team5.features.retrospect.screen.visualtransmation.CurrencyVisualTransformation


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
                    focusedContainerColor = HedgeColor.WHITE,
                    unfocusedContainerColor = HedgeColor.WHITE,

                    focusedIndicatorColor = HedgeColor.Transparent,
                    unfocusedIndicatorColor = HedgeColor.Transparent,
                    disabledIndicatorColor = HedgeColor.Transparent,
                    errorIndicatorColor = HedgeColor.Transparent,

                    focusedLabelColor = HedgeColor.Brand.Darken,
                    unfocusedLabelColor = HedgeColor.Text.Assistive,

                    focusedPlaceholderColor = HedgeColor.Text.Assistive,
                    unfocusedPlaceholderColor = HedgeColor.Text.Assistive,

                    focusedTextColor = HedgeColor.Text.Title,
                    unfocusedTextColor = HedgeColor.Text.Title,

                    cursorColor = HedgeColor.Brand.Darken,
                    errorCursorColor = HedgeColor.Transparent,
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
                    focusedContainerColor = HedgeColor.WHITE,
                    unfocusedContainerColor = HedgeColor.WHITE,
                    disabledContainerColor = HedgeColor.WHITE,

                    errorLabelColor = HedgeColor.Feedback.Error,
                    errorTextColor = HedgeColor.Text.Title,
                    errorContainerColor = HedgeColor.Transparent,

                    focusedIndicatorColor = HedgeColor.Transparent,
                    unfocusedIndicatorColor = HedgeColor.Transparent,
                    disabledIndicatorColor = HedgeColor.Transparent,
                    errorIndicatorColor = HedgeColor.Transparent,

                    focusedLabelColor = HedgeColor.Brand.Darken,
                    unfocusedLabelColor = HedgeColor.Text.Assistive,

                    focusedPlaceholderColor = HedgeColor.Text.Assistive,
                    unfocusedPlaceholderColor = HedgeColor.Text.Assistive,

                    focusedTextColor = HedgeColor.Text.Title,
                    unfocusedTextColor = HedgeColor.Text.Title,

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

    var selectedIndex by remember { mutableIntStateOf(0) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    var currentVisualTransformation by remember(selectedIndex) {
        mutableStateOf(
            CurrencyVisualTransformation(if (selectedIndex == 0) KRW else USD)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.gray200)),
        verticalArrangement = Arrangement.Center
    ) {
        HedgeUnitTextField(
            label = stringResource(R.string.retrospect_selling_price),
            placeholder = stringResource(R.string.retrospect_selling_price_placeholder),
            value = TextFieldValue(
                text = textFieldValue.text,
                selection = textFieldValue.selection
            ),
            onValueChange = { newValue ->
                val digitsOnlyText = newValue.text.filter { it.isDigit() }
                val newCursorPosition = newValue.selection.start.let { transformedOffset ->
                    newValue.text.substring(0, transformedOffset).count { it.isDigit() }
                }.coerceIn(0, digitsOnlyText.length)

                textFieldValue = textFieldValue.copy(
                    text = digitsOnlyText,
                    selection = TextRange(newCursorPosition)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(),
            trailingIcon = {
                HedgeSegment(
                    options = listOf(
                        stringResource(id = R.string.retrospect_unit_won),
                        stringResource(id = R.string.retrospect_unit_dollar)
                    ),
                    selectedIndex = selectedIndex,
                    onSelectedIndexChange = { index ->
                        selectedIndex = index
                    }
                )
            },
            visualTransformation = currentVisualTransformation
        )
    }
}