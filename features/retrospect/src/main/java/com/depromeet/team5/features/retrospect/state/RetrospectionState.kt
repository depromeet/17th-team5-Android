package com.depromeet.team5.features.retrospect.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import com.depromeet.team5.features.retrospect.annotation.CurrencyType
import com.depromeet.team5.features.retrospect.annotation.ReturnSignType


@Stable
data class RetrospectionState(
    val priceTextFieldState: MutableState<TextFieldState>,
    val stockTextFieldState: MutableState<TextFieldState>,
    val dateTextFieldState: MutableState<TextFieldState>,
    val returnTextFieldState: MutableState<TextFieldState>,
    val returnToggleState: MutableState<Boolean>,
    val currencyType: MutableState<CurrencyType>,
    val returnSignType: MutableState<ReturnSignType>
)
