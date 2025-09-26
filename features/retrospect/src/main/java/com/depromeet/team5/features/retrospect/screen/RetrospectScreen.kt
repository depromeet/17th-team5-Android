package com.depromeet.team5.features.retrospect.screen

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeSegment
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.model.request.RequestViewModel
import com.depromeet.team5.features.retrospect.R
import com.depromeet.team5.features.retrospect.annotation.DATE
import com.depromeet.team5.features.retrospect.annotation.RETURN
import com.depromeet.team5.features.retrospect.annotation.SELLING
import com.depromeet.team5.features.retrospect.annotation.STOCK
import com.depromeet.team5.features.retrospect.screen.component.HedgeDatePickerDialog
import com.depromeet.team5.features.retrospect.screen.component.HedgeSimpleTextField
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
    onBackPressed: () -> Unit,
    onClickedConfirmButton: () -> Unit,
    requestViewModel: RequestViewModel,
    modifier: Modifier = Modifier,
    viewModel: RetrospectViewModel = hiltViewModel(),
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
        onClickedConfirmButton = {
            if (!viewModel.okButtonState.stateFlow.value) return@RetrospectScreen

            requestViewModel.request = requestViewModel.request.copy(
                price = viewModel.sellingTextFieldState.stateFlow.value.text.toInt(),
                volume = viewModel.stockTextFieldState.stateFlow.value.text.toInt(),
                orderDate = formatDate(
                    viewModel.dateTextFieldState.stateFlow.value.text
                ),
                returnRate = try {
                    viewModel.returnTextFieldState.stateFlow.value.text.toDouble()
                } catch (e: Exception) {
                    null
                }
            )

            Log.e("requestViewModel", requestViewModel.request.toString())

            onClickedConfirmButton()
        },
        onUpdateReturnToggle = { isToggled ->
            viewModel.returnToggleState.update { isToggled }
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
            style = HedgeTypography.Headline1.SemiBold,
            color = HedgeColor.GREY_900
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
                        style = HedgeTypography.Body3.SemiBold,
                        color = HedgeColor.GREY_700
                    )
                    Text(
                        modifier = Modifier.padding(top = 1.dp),
                        text = stringResource(id = R.string.retrospect_ai_analysis_description),
                        style = HedgeTypography.Label2.SemiBold,
                        color = HedgeColor.Text.Alternative
                    )
                }

                Switch(
                    checked = returnToggleState,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = HedgeColor.WHITE,
                        uncheckedThumbColor = HedgeColor.WHITE,
                        checkedTrackColor = HedgeColor.Brand.Darken,
                        uncheckedTrackColor = HedgeColor.GREY_OPACITY_300
                    ),
                    onCheckedChange = onUpdateReturnToggle
                )
            }

            HedgeButton.Action.Filled(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                enabled = buttonEnabled,
                text = stringResource(id = R.string.retrospect_confirm),
                onClick = onClickedConfirmButton
            )
        }
    }
}

@Composable
private fun SellingTextField(
    state: TextFieldState,
    onUpdateSellingText: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var selectedIndex by remember { mutableIntStateOf(0) }
    val currentVisualTransformation =
        if (selectedIndex == 1) USDCurrencyVisualTransformation() else KoreanCurrencyVisualTransformation()

    HedgeUnitTextField(
        modifier = modifier.focusRequester(focusRequester),
        label = stringResource(R.string.retrospect_selling_price),
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

@Composable
private fun StockTextField(
    state: TextFieldState,
    onUpdateStockText: (String, Int) -> Unit,
    modifier: Modifier = Modifier
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
        label = stringResource(R.string.retrospect_volume),
        placeholder = stringResource(id = R.string.retrospect_volume_placeholder),
        visualTransformation = UnitTransformation(stringResource(id = R.string.retrospect_unit_stock)),
        onDone = {
            focusManager.moveFocus(FocusDirection.Down)
        }
    )
}

@Composable
private fun SimpleNumberTextField(
    value: TextFieldValue,
    label: String,
    placeholder: String,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
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
    state: TextFieldState,
    onUpdateDateText: (String, Int) -> Unit,
    onErrorDateText: (String, Int) -> Unit,
    onFocusChanged: () -> Unit,
    modifier: Modifier = Modifier
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
                    formatDate("YYYY년 MM월 dd일", it)
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
        label = if (state.isError) {
            state.label
        } else {
            stringResource(R.string.retrospect_transaction_date)
        },
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

    var selectedIndex by remember { mutableIntStateOf(0) }

    HedgeUnitTextField(
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onChangedKeyboardVisibility(false)
            }
        ),
        trailingIcon = {
            HedgeSegment(
                options = listOf(
                    stringResource(id = R.string.retrospect_unit_plus),
                    stringResource(id = R.string.retrospect_unit_minus)
                ),
                selectedIndex = selectedIndex,
                onSelectedIndexChange = { index ->
                    selectedIndex = index
                }
            )
        },
        visualTransformation = UnitTransformation(stringResource(id = R.string.retrospect_unit_percent))
    )
}

private fun formatDate(date: String): String {
    val regex = """(\d{4})년\s*(\d{1,2})월\s*(\d{1,2})일""".toRegex()

    val matchResult = regex.find(date)

    if (matchResult != null) {
        val (year, month, day) = matchResult.destructured

        val formattedMonth = month.padStart(2, '0')
        val formattedDay = day.padStart(2, '0')

        return "$year-$formattedMonth-$formattedDay"
    }

    error("잘못된 Date Format이 들어왔습니다. Params : { $date }")
}

private fun formatDate(format: String, milliseconds: Long): String {
    val formatter = SimpleDateFormat(format, Locale.KOREA)
    return formatter.format(Date(milliseconds))
}

@Composable
private fun CompanyTitle(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(22.dp)
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
