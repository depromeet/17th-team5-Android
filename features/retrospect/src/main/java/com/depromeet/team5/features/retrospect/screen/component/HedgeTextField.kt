package com.depromeet.team5.features.retrospect.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depromeet.team5.features.retrospect.R
import com.depromeet.team5.features.retrospect.screen.CurrencyVisualTransformation
import com.depromeet.team5.features.retrospect.screen.annotation.CurrencyUnit
import com.depromeet.team5.features.retrospect.screen.annotation.KOREAN
import com.depromeet.team5.features.retrospect.screen.annotation.NIL
import com.depromeet.team5.features.retrospect.screen.annotation.USD


@Composable
internal fun HedgeTextField(
    modifier: Modifier = Modifier,
    unit: CurrencyUnit = NIL,
    label: String,
    placeholder: String,
    trailingIcon: (@Composable (Boolean, (CurrencyUnit) -> Unit) -> Unit)? = null,
) {
    var borderColor by remember { mutableIntStateOf(R.color.brand500) }
    var inputText by remember { mutableStateOf("") }
    var isToggled by remember { mutableStateOf(false) }
    var currentUnit by remember { mutableStateOf(unit) }
    val visualTransformation = remember(currentUnit) { CurrencyVisualTransformation(currentUnit) }

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
                value = inputText,
                onValueChange = { inputText = it },
                visualTransformation = visualTransformation,
                label = {
                    Text(
                        text = label,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                },
                trailingIcon = {
                    trailingIcon?.invoke(isToggled) { isCurrentUnit ->
                        isToggled = !isToggled
                        inputText = ""
                        currentUnit = isCurrentUnit
                    }
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
private fun HedgeTextFieldPreview() {
    var currentUnit by remember { mutableStateOf(KOREAN) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.gray200)),
        contentAlignment = Alignment.Center
    ) {
        HedgeTextField(
            label = "매도가",
            placeholder = "매도 가격",
            trailingIcon = { isToggled, onToggleChanged ->
                HedgeSwitch(
                    isToggled = isToggled,
                    onToggleChanged = onToggleChanged,
                    offUnit = KOREAN,
                    onUnit = USD,
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
            unit = currentUnit
        )
    }
}