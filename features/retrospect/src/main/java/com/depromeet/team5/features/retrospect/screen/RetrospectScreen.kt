package com.depromeet.team5.features.retrospect.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.features.retrospect.R
import com.depromeet.team5.features.retrospect.annotation.DATE
import com.depromeet.team5.features.retrospect.annotation.RETURN
import com.depromeet.team5.features.retrospect.annotation.SELLING
import com.depromeet.team5.features.retrospect.annotation.STOCK
import com.depromeet.team5.features.retrospect.screen.component.HedgeDatePickerDialog
import com.depromeet.team5.features.retrospect.screen.component.HedgeSimpleTextField
import com.depromeet.team5.features.retrospect.screen.component.HedgeSwitch
import com.depromeet.team5.features.retrospect.screen.component.HedgeTopbar
import com.depromeet.team5.features.retrospect.screen.component.HedgeUnitTextField
import com.depromeet.team5.features.retrospect.screen.visualtransmation.KoreanCurrencyVisualTransformation
import com.depromeet.team5.features.retrospect.screen.visualtransmation.USDCurrencyVisualTransformation
import com.depromeet.team5.features.retrospect.screen.visualtransmation.UnitTransformation
import com.depromeet.team5.features.retrospect.state.TextFieldState
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun RetrospectRoute(
    modifier: Modifier,
    viewModel: RetrospectViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val sellingTextFieldState by viewModel.sellingTextFieldState.stateFlow.collectAsStateWithLifecycle()
    val stockTextFieldState by viewModel.stockTextFieldState.stateFlow.collectAsStateWithLifecycle()
    val dateTextFieldState by viewModel.dateTextFieldState.stateFlow.collectAsStateWithLifecycle()
    val returnTextFieldState by viewModel.returnTextFieldState.stateFlow.collectAsStateWithLifecycle()
    val buttonState by viewModel.okButtonState.stateFlow.collectAsStateWithLifecycle()
    val returnToggleState by viewModel.returnToggleState.stateFlow.collectAsStateWithLifecycle()

    RetrospectScreen(
        modifier = modifier,
        sellingTextFieldState = sellingTextFieldState,
        stockTextFieldState = stockTextFieldState,
        dateTextFieldState = dateTextFieldState,
        returnTextFieldState = returnTextFieldState,
        buttonEnabled = buttonState,
        returnToggleState = returnToggleState,
        onUpdateSellingText = { text, selection ->
            viewModel.updateState(SELLING, text, selection)
        },
        onUpdateStockText = { text, selection ->
            viewModel.updateState(STOCK, text, selection)
        },
        onUpdateDateText = { text, selection ->
            viewModel.updateState(DATE, text, selection)
        },
        onUpdateReturnText = { text, selection ->
            viewModel.updateState(RETURN, text, selection)
        },
        onErrorDateText = { text, selection ->
            viewModel.updateState(RETURN, text, selection, true)
        },
        onClickedConfirmButton = viewModel::onClickedConfirmButton,
        onUpdateReturnToggle = {
            viewModel.returnToggleState.update { it }
        },
        onBackPressed = onBackPressed
    )
}

@Composable
private fun RetrospectScreen(
    sellingTextFieldState: TextFieldState,
    stockTextFieldState: TextFieldState,
    dateTextFieldState: TextFieldState,
    returnTextFieldState: TextFieldState,
    buttonEnabled: Boolean,
    returnToggleState: Boolean,
    onUpdateSellingText: (String, Int) -> Unit,
    onUpdateStockText: (String, Int) -> Unit,
    onUpdateDateText: (String, Int) -> Unit,
    onErrorDateText: (String, Int) -> Unit,
    onUpdateReturnText: (String, Int) -> Unit,
    onClickedConfirmButton: () -> Unit,
    onUpdateReturnToggle: (Boolean) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.background_secondary))
    ) {
        HedgeTopbar { }
        CompanyTitle(
            modifier = Modifier.padding(start = 16.dp, top = 10.dp)
        )

        Text(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            text = stringResource(id = R.string.retrospect_selling_price_title),
            fontWeight = FontWeight.W600,
            fontSize = 22.sp,
            color = colorResource(R.color.primary)
        )

        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 26.dp, end = 20.dp)
                .background(
                    color = colorResource(R.color.white),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            SellingTextField(
                state = sellingTextFieldState,
                onUpdateSellingText = onUpdateSellingText
            )
            StockTextField(
                state = stockTextFieldState,
                onUpdateStockText = onUpdateStockText
            )
            DateTextField(
                state = dateTextFieldState,
                onUpdateDateText = onUpdateDateText,
                onErrorDateText = onErrorDateText,
                onFocusChanged = {
                    if (returnToggleState) {
                        focusManager.moveFocus(FocusDirection.Down)
                    } else {
                        focusManager.clearFocus()
                    }
                }
            )
        }

        AnimatedVisibility(
            visible = returnToggleState,
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
            ReturnTextField(
                state = returnTextFieldState,
                onUpdateReturnText = onUpdateReturnText,
                onChangedKeyboardVisibility = {
                    if (it) {
                        keyboardController?.show()
                    } else {
                        keyboardController?.hide()
                    }
                }
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
                        text = stringResource(id = R.string.retrospect_enter_rate_of_return),
                        color = colorResource(R.color.gray700),
                        fontSize = 15.sp,
                        letterSpacing = 0.14.sp,
                        fontWeight = FontWeight.W600
                    )
                    Text(
                        text = stringResource(id = R.string.retrospect_ai_analysis_description),
                        color = colorResource(R.color.alternative),
                        fontSize = 13.sp,
                        letterSpacing = 0.03.sp,
                        fontWeight = FontWeight.W600
                    )
                }

                Switch(
                    checked = returnToggleState,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(R.color.white),
                        uncheckedThumbColor = colorResource(R.color.white),
                        checkedTrackColor = colorResource(R.color.gray300),
                        uncheckedTrackColor = colorResource(R.color.gray300)
                    ),
                    onCheckedChange = onUpdateReturnToggle
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
                enabled = buttonEnabled,
                onClick = onClickedConfirmButton
            ) {
                Text(
                    text = stringResource(id = R.string.retrospect_confirm),
                    color = colorResource(R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}

@Composable
private fun SellingTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    onUpdateSellingText: (String, Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var isToggled by remember { mutableStateOf(false) }
    val currentVisualTransformation =
        if (isToggled) USDCurrencyVisualTransformation() else KoreanCurrencyVisualTransformation()

    HedgeUnitTextField(
        modifier = modifier.focusRequester(focusRequester),
        label = state.label,
        placeholder = stringResource(id = R.string.retrospect_selling_price_placeholder),
        value = TextFieldValue(
            text = state.text,
            selection = TextRange(state.selection)
        ),
        onValueChange = { newValue ->
            val digitsOnlyText = newValue.text.filter { it.isDigit() }
            val newCursorPosition = newValue.selection.start.let { transformedOffset ->
                newValue.text.substring(0, transformedOffset).count { it.isDigit() }
            }.coerceIn(0, digitsOnlyText.length)

            onUpdateSellingText(digitsOnlyText, newCursorPosition)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        trailingIcon = {
            CurrencySwitch(
                isToggled = isToggled,
                onToggleChanged = { newToggleState ->
                    isToggled = newToggleState
                    onUpdateSellingText("", 0)
                }
            )
        },
        visualTransformation = currentVisualTransformation
    )
}

@Composable
private fun StockTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    onUpdateStockText: (String, Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    SimpleNumberTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = TextFieldValue(
            text = state.text,
            selection = TextRange(state.selection)
        ),
        onValueChange = { newValue ->
            val digitsOnlyText = newValue.text.filter { it.isDigit() }
            val newCursorPosition = newValue.selection.start.let { transformedOffset ->
                newValue.text.substring(0, transformedOffset).count { it.isDigit() }
            }.coerceIn(0, digitsOnlyText.length)

            onUpdateStockText(digitsOnlyText, newCursorPosition)
        },
        label = state.label,
        placeholder = stringResource(id = R.string.retrospect_volume_placeholder),
        visualTransformation = UnitTransformation(stringResource(id = R.string.retrospect_unit_stock)),
        onDone = {
            focusManager.moveFocus(FocusDirection.Down)
        }
    )
}

@Composable
private fun SimpleNumberTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onDone: KeyboardActionScope.() -> Unit = {}
) {
    HedgeSimpleTextField(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = onDone
        ),
        visualTransformation = visualTransformation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    onUpdateDateText: (String, Int) -> Unit,
    onErrorDateText: (String, Int) -> Unit,
    onFocusChanged: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var isShowDatePicker by remember { mutableStateOf(false) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val datePickerState = rememberDatePickerState(
        yearRange = currentYear - 100..currentYear
    )

    if (isShowDatePicker) {
        HedgeDatePickerDialog(
            datePickerState = datePickerState,
            onClickConfirm = {
                isShowDatePicker = false

                val date = datePickerState.selectedDateMillis?.let {
                    formatDate(it)
                } ?: run {
                    focusManager.clearFocus()
                    return@HedgeDatePickerDialog
                }

                val digitsOnlyText = date.filter { it.isDigit() }

                val current = LocalDate.now()

                val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                val selectedDate = LocalDate.parse(digitsOnlyText, formatter)

                if (selectedDate.isAfter(current)) {
                    onErrorDateText(date, digitsOnlyText.length)
                } else {
                    onUpdateDateText(date, digitsOnlyText.length)
                }

                onFocusChanged()
            },
            onClickDismiss = {
                isShowDatePicker = false
                focusManager.clearFocus()
            },
            onDismissRequest = {
                isShowDatePicker = false
                focusManager.clearFocus()
            }
        )
    }

    HedgeSimpleTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { isShowDatePicker = it.isFocused },
        value = TextFieldValue(state.text, TextRange(state.selection)),
        onValueChange = {},
        label = state.label,
        placeholder = stringResource(id = R.string.retrospect_transaction_date),
        isError = state.isError,
        readOnly = true
    )
}

@Composable
fun ReturnTextField(
    state: TextFieldState,
    onUpdateReturnText: (String, Int) -> Unit,
    onChangedKeyboardVisibility: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    SimpleNumberTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(start = 20.dp, end = 20.dp, top = 12.dp),
        label = stringResource(id = R.string.retrospect_rate_of_return),
        placeholder = stringResource(id = R.string.retrospect_unit_percent),
        value = TextFieldValue(state.text, TextRange(state.selection)),
        onValueChange = { newValue ->
            val digitsOnlyText = newValue.text.filter { it.isDigit() }
            val newCursorPosition = newValue.selection.start.let { transformedOffset ->
                newValue.text.substring(0, transformedOffset).count { it.isDigit() }
            }.coerceIn(0, digitsOnlyText.length)

            onUpdateReturnText(digitsOnlyText, newCursorPosition)
        },
        onDone = {
            focusManager.clearFocus()
            onChangedKeyboardVisibility(false)
        },
        visualTransformation = UnitTransformation(stringResource(id = R.string.retrospect_unit_percent))
    )
}

private fun getCalendar(year: Int, month: Int, day: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, day)
    return calendar
}

private fun formatDate(milliseconds: Long): String {
    val formatter = SimpleDateFormat("YYYY년 MM월 dd일", Locale.KOREA)
    return formatter.format(Date(milliseconds))
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
                    text = stringResource(id = R.string.retrospect_unit_won),
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
                    text = stringResource(id = R.string.retrospect_unit_dollar),
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
            text = stringResource(id = R.string.retrospect_company_name_temp)
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
    val context = LocalContext.current

    var buttonState by remember { mutableStateOf(false) }

    var returnToggleState by remember { mutableStateOf(false) }

    var sellingTextFieldState by remember {
        mutableStateOf(
            TextFieldState.EMPTY.copy(
                label = context.getString(
                    R.string.retrospect_selling_price
                )
            )
        )
    }
    var stockTextFieldState by remember {
        mutableStateOf(
            TextFieldState.EMPTY.copy(label = context.getString(R.string.retrospect_volume))
        )
    }
    var dateTextFieldState by remember {
        mutableStateOf(
            TextFieldState.EMPTY.copy(label = context.getString(R.string.retrospect_transaction_date))
        )
    }
    var returnTextFieldState by remember {
        mutableStateOf(TextFieldState.EMPTY.copy(label = context.getString(R.string.retrospect_rate_of_return)))
    }

    LaunchedEffect(
        returnToggleState,
        sellingTextFieldState,
        stockTextFieldState,
        dateTextFieldState,
        returnTextFieldState
    ) {
        val array = arrayOf(sellingTextFieldState, stockTextFieldState, dateTextFieldState)

        val isResult = array.all { it.text.isNotEmpty() && !it.isError }

        buttonState = if (returnToggleState) {
            isResult && (returnTextFieldState.text.isNotEmpty() && !returnTextFieldState.isError)
        } else {
            isResult
        }
    }

    Scaffold { paddingValues ->
        RetrospectScreen(
            modifier = Modifier.padding(paddingValues),
            sellingTextFieldState = sellingTextFieldState,
            stockTextFieldState = stockTextFieldState,
            dateTextFieldState = dateTextFieldState,
            returnTextFieldState = returnTextFieldState,
            buttonEnabled = buttonState,
            returnToggleState = returnToggleState,
            onUpdateSellingText = { text, selection ->
                sellingTextFieldState =
                    sellingTextFieldState.copy(text = text, selection = selection)
            },
            onUpdateStockText = { text, selection ->
                stockTextFieldState = stockTextFieldState.copy(text = text, selection = selection)
            },
            onUpdateDateText = { text, selection ->
                dateTextFieldState = dateTextFieldState.copy(
                    label = context.getString(R.string.retrospect_transaction_date),
                    text = text,
                    selection = selection,
                    isError = false
                )
            },
            onUpdateReturnText = { text, selection ->
                returnTextFieldState =
                    returnTextFieldState.copy(text = text, selection = selection)
            },
            onErrorDateText = { text, selection ->
                dateTextFieldState = dateTextFieldState.copy(
                    label = context.getString(R.string.error_message_future_date),
                    text = text,
                    selection = selection,
                    isError = true
                )
            },
            onClickedConfirmButton = {},
            onUpdateReturnToggle = { returnToggleState = it },
            onBackPressed = {}
        )
    }
}
