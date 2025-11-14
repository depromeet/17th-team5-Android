package com.depromeet.team5.features.retrospect.screen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.depromeet.team5.core.designsystem.component.HedgeButton
import com.depromeet.team5.core.designsystem.component.HedgeSegment
import com.depromeet.team5.core.designsystem.component.HedgeTopBar
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.designsystem.foundation.HedgeIcon
import com.depromeet.team5.core.designsystem.foundation.HedgeTypography
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.PrincipleChecks
import com.depromeet.team5.core.domain.model.PrincipleGroupState
import com.depromeet.team5.core.domain.model.PrincipleState
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import com.depromeet.team5.core.navigation.request.RequestViewModel
import com.depromeet.team5.core.ui.component.PrincipleBottomSheetDialog
import com.depromeet.team5.features.retrospect.R
import com.depromeet.team5.features.retrospect.annotation.CurrencyType
import com.depromeet.team5.features.retrospect.annotation.ReturnSignType
import com.depromeet.team5.features.retrospect.screen.component.HedgeDatePickerDialog
import com.depromeet.team5.features.retrospect.screen.component.HedgeSimpleTextField
import com.depromeet.team5.features.retrospect.screen.component.HedgeUnitTextField
import com.depromeet.team5.features.retrospect.screen.visualtransmation.CurrencyVisualTransformation
import com.depromeet.team5.features.retrospect.screen.visualtransmation.UnitVisualTransformation
import com.depromeet.team5.features.retrospect.state.RetrospectionState
import com.depromeet.team5.features.retrospect.state.TextFieldState
import com.depromeet.team5.features.retrospect.state.UiState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun RetrospectRoute(
    requestViewModel: RequestViewModel,
    modifier: Modifier = Modifier,
    onClickedConfirmButton: () -> Unit,
    onBackPressed: () -> Unit,
    onShowErrorToast: (Throwable) -> Unit
) {
    val context = LocalContext.current

    val retrospectionState = rememberRetrospectionState(
        requestParams = requestViewModel.request
    )

    var isOpenBottomSheetDialog by remember { mutableStateOf(false) }

    val isButtonEnabled by remember {
        derivedStateOf {
            arrayOf(
                retrospectionState.sellingTextFieldState.value,
                retrospectionState.stockTextFieldState.value,
                retrospectionState.dateTextFieldState.value
            )
                .all { it.text.isNotEmpty() && !it.isError }
                .let { isActive ->
                    val returnIsActive = if (retrospectionState.returnToggleState.value) {
                        retrospectionState.returnTextFieldState.value.text.isNotEmpty() &&
                            !retrospectionState.returnTextFieldState.value.isError
                    } else {
                        true
                    }

                    isActive && returnIsActive
                }
        }
    }

    RetrospectScreen(
        modifier = modifier,
        retrospectionState = retrospectionState,
        requestParams = requestViewModel.request,
        companyLogoUrl = requestViewModel.companyLogoUrl,
        buttonEnabled = isButtonEnabled,
        onUpdateSellingText = { text, selection ->
            retrospectionState.sellingTextFieldState.value =
                retrospectionState.sellingTextFieldState.value.copy(
                    text = text,
                    selection = selection
                )
        },
        onUpdateStockText = { text, selection ->
            retrospectionState.stockTextFieldState.value =
                retrospectionState.stockTextFieldState.value.copy(
                    text = text,
                    selection = selection
                )
        },
        onUpdateDateText = { text, selection ->
            retrospectionState.dateTextFieldState.value =
                retrospectionState.dateTextFieldState.value.copy(
                    label = context.getString(R.string.retrospect_transaction_date),
                    text = text,
                    selection = selection,
                    isError = false
                )
        },
        onErrorDateText = { text, selection ->
            retrospectionState.dateTextFieldState.value =
                retrospectionState.dateTextFieldState.value.copy(
                    label = context.getString(R.string.error_message_future_date),
                    text = text,
                    selection = selection,
                    isError = true
                )
        },
        onUpdateReturnText = { text, selection ->
            retrospectionState.returnTextFieldState.value =
                retrospectionState.returnTextFieldState.value.copy(
                    text = text,
                    selection = selection
                )
        },
        onClickedConfirmButton = {
            if (!isButtonEnabled) return@RetrospectScreen
            isOpenBottomSheetDialog = true
        },
        onUpdateReturnToggle = { isToggled ->
            retrospectionState.returnToggleState.value = isToggled
        },
        onUpdateCurrency = { currency ->
            retrospectionState.currencyType.value = currency
        },
        onUpdateReturnSign = { returnSignType ->
            retrospectionState.returnSignType.value = returnSignType
        },
        onBackPressed = onBackPressed
    )

    if (isOpenBottomSheetDialog) {
        PrincipleDialog(
            orderType = requestViewModel.request.orderType,
            onClickedClose = { isOpenBottomSheetDialog = false },
            onClickedConfirmButton = { myPrincipleGroup ->
                with(retrospectionState) {
                    requestViewModel.request = requestViewModel.request.copy(
                        price = sellingTextFieldState.value.text.toInt(),
                        volume = stockTextFieldState.value.text.toInt(),
                        orderDate = dateTextFieldState.value.text,
                        currency = CurrencyType.from(currencyType.value),
                        returnRate = try {
                            when (returnSignType.value) {
                                ReturnSignType.Plus -> {
                                    returnTextFieldState.value.text.toDouble()
                                }

                                ReturnSignType.Minus -> {
                                    returnTextFieldState.value.text.toDouble() * -1
                                }
                            }
                        } catch (e: Exception) {
                            null
                        }
                    )

                    requestViewModel.selectedMyPrincipleGroupState = PrincipleGroupState(
                        id = myPrincipleGroup.id,
                        groupName = myPrincipleGroup.groupName,
                        thumbnail = myPrincipleGroup.thumbnail,
                        principleType = requestViewModel.request.orderType.toPrincipleType(),
                        principles = myPrincipleGroup.principles.map {
                            PrincipleState(
                                id = it.id,
                                groupId = it.groupId,
                                principle = it.principle,
                                description = it.description,
                                principleChecks = PrincipleChecks.createInit(it.id)
                            )
                        }

                    )
                }

                onClickedConfirmButton()
            },
            onShowErrorToast = {
                onShowErrorToast(it)
                isOpenBottomSheetDialog = false
            }
        )
    }
}

@Composable
private fun rememberRetrospectionState(
    requestParams: CreateRetrospectionRequest,
    context: Context = LocalContext.current
): RetrospectionState {
    val sellingTextFieldState = rememberSaveable {
        getTextFieldState(
            label = if (requestParams.orderType == OrderType.SELL) {
                context.getString(R.string.retrospect_selling_price)
            } else {
                context.getString(R.string.retrospect_buy_price)
            }
        )
    }
    val stockTextFieldState = rememberSaveable {
        getTextFieldState(label = context.getString(R.string.retrospect_volume))
    }
    val dateTextFieldState = rememberSaveable {
        getTextFieldState(label = context.getString(R.string.retrospect_transaction_date))
    }
    val returnTextFieldState = rememberSaveable {
        getTextFieldState(label = context.getString(R.string.retrospect_rate_of_return))
    }
    val buttonState = rememberSaveable { mutableStateOf(false) }
    val returnToggleState = rememberSaveable { mutableStateOf(false) }
    val currencyState: MutableState<CurrencyType> =
        rememberSaveable { mutableStateOf(CurrencyType.KRW) }
    val returnSignState: MutableState<ReturnSignType> =
        rememberSaveable { mutableStateOf(ReturnSignType.Plus) }

    return remember(
        sellingTextFieldState,
        stockTextFieldState,
        dateTextFieldState,
        returnTextFieldState,
        buttonState,
        returnToggleState,
        currencyState,
        returnSignState
    ) {
        RetrospectionState(
            sellingTextFieldState = sellingTextFieldState,
            stockTextFieldState = stockTextFieldState,
            dateTextFieldState = dateTextFieldState,
            returnTextFieldState = returnTextFieldState,
            returnToggleState = returnToggleState,
            currencyType = currencyState,
            returnSignType = returnSignState
        )
    }
}

@Composable
private fun RetrospectScreen(
    retrospectionState: RetrospectionState,
    companyLogoUrl: String?,
    requestParams: CreateRetrospectionRequest,
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean = false,
    onUpdateSellingText: (String, Int) -> Unit,
    onUpdateStockText: (String, Int) -> Unit,
    onUpdateDateText: (String, Int) -> Unit,
    onErrorDateText: (String, Int) -> Unit,
    onUpdateReturnText: (String, Int) -> Unit,
    onClickedConfirmButton: () -> Unit,
    onUpdateReturnToggle: (Boolean) -> Unit,
    onUpdateCurrency: (CurrencyType) -> Unit,
    onUpdateReturnSign: (ReturnSignType) -> Unit,
    onBackPressed: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var isShowFirstDivider by remember { mutableStateOf(false) }
    var isShowSecondDivider by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .imePadding()
            .fillMaxSize()
            .background(color = HedgeColor.Neutral.BackgroundSecondary)
            .verticalScroll(rememberScrollState())
    ) {
        HedgeTopBar(
            modifier = Modifier.statusBarsPadding(),
            onClickBack = onBackPressed
        )

        Row(
            modifier = Modifier.padding(start = 20.dp, top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(22.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(20.dp))
            ) {
                if (companyLogoUrl != null) {
                    AsyncImage(
                        modifier = modifier.fillMaxSize(),
                        model = companyLogoUrl,
                        contentDescription = null
                    )
                } else {
                    Image(
                        modifier = modifier.fillMaxSize(),
                        imageVector = HedgeIcon.COMPANY_LOGO,
                        contentDescription = null
                    )
                }
            }

            Text(
                modifier = Modifier.padding(start = 7.dp),
                style = HedgeTypography.Body3.Medium,
                color = HedgeColor.GREY_900,
                text = requestParams.companyName
            )
        }

        Text(
            modifier = Modifier.padding(start = 20.dp, top = 8.dp),
            text = if (requestParams.orderType == OrderType.SELL) {
                stringResource(id = R.string.retrospect_selling_price_title)
            } else {
                stringResource(id = R.string.retrospect_buy_price_title)
            },
            style = HedgeTypography.Headline1.SemiBold,
            color = HedgeColor.GREY_900
        )

        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 26.dp, end = 20.dp)
                .background(
                    color = HedgeColor.WHITE,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            SellingTextField(
                state = retrospectionState.sellingTextFieldState,
                requestParams = requestParams,
                currencyTypeState = retrospectionState.currencyType,
                onUpdateSellingText = onUpdateSellingText,
                onUpdateCurrency = onUpdateCurrency,
                onFocusChanged = { isFocused ->
                    isShowFirstDivider = !isFocused
                }
            )

            if (isShowFirstDivider) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = HedgeColor.GREY_200
                )
            }

            StockTextField(
                state = retrospectionState.stockTextFieldState,
                onUpdateStockText = onUpdateStockText,
                onFocusChanged = { isFocused ->
                    isShowFirstDivider = !isFocused
                    isShowSecondDivider = !isFocused
                }
            )

            if (isShowSecondDivider) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = HedgeColor.GREY_200
                )
            }

            DateTextField(
                state = retrospectionState.dateTextFieldState,
                onUpdateDateText = onUpdateDateText,
                onErrorDateText = onErrorDateText,
                onFocusChanged = {
                    if (retrospectionState.returnToggleState.value) {
                        focusManager.moveFocus(FocusDirection.Down)
                    } else {
                        focusManager.clearFocus()
                    }
                }
            )
        }

        AnimatedVisibility(
            visible = retrospectionState.returnToggleState.value,
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
                state = retrospectionState.returnTextFieldState,
                returnSignTypeState = retrospectionState.returnSignType,
                onUpdateReturnText = onUpdateReturnText,
                onUpdateReturnSign = onUpdateReturnSign,
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
            if (requestParams.orderType == OrderType.SELL) {
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
                        checked = retrospectionState.returnToggleState.value,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = HedgeColor.WHITE,
                            uncheckedThumbColor = HedgeColor.WHITE,
                            checkedTrackColor = HedgeColor.Brand.Darken,
                            uncheckedTrackColor = HedgeColor.GREY_OPACITY_300
                        ),
                        onCheckedChange = onUpdateReturnToggle
                    )
                }
            }

            HedgeButton.Action.Filled(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 31.dp),
                enabled = buttonEnabled,
                forceClickable = buttonEnabled,
                text = stringResource(id = R.string.retrospect_confirm),
                onClick = onClickedConfirmButton
            )
        }
    }
}

@Composable
private fun PrincipleDialog(
    orderType: OrderType,
    onClickedClose: () -> Unit,
    onClickedConfirmButton: (MyPrincipleGroup) -> Unit,
    onShowErrorToast: (Throwable) -> Unit,
    viewModel: PrincipleGroupViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getPrincipleGroups(orderType)
    }

    when (val state = uiState) {
        is UiState.Success<MyPrincipleGroup> -> {
            PrincipleBottomSheetDialog(
                title = stringResource(R.string.principle_bottom_sheet_dialog_title),
                defaultPrincipleGroup = state.defaultPrincipleGroup,
                myPrincipleGroups = state.myPrincipleGroups,
                isShowAddButton = false,
                onClickedClose = onClickedClose,
                onClickedConfirmButton = onClickedConfirmButton
            )
        }

        is UiState.Error -> {
            state.throwable?.let {
                onShowErrorToast(it)
                onClickedClose()
            }
        }

        else -> {}
    }
}

@Composable
private fun SellingTextField(
    state: State<TextFieldState>,
    currencyTypeState: State<CurrencyType>,
    requestParams: CreateRetrospectionRequest,
    modifier: Modifier = Modifier,
    onUpdateSellingText: (String, Int) -> Unit,
    onUpdateCurrency: (CurrencyType) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var selectedIndex by remember {
        val currency = when (currencyTypeState.value) {
            CurrencyType.KRW -> 0
            CurrencyType.USD -> 1
            else -> 0
        }

        mutableIntStateOf(currency)
    }
    val currentVisualTransformation by remember(selectedIndex) {
        mutableStateOf(
            CurrencyVisualTransformation(
                if (selectedIndex == 0) CurrencyType.KRW else CurrencyType.USD
            )
        )
    }

    HedgeUnitTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        label = state.value.label,
        placeholder = if (requestParams.orderType == OrderType.SELL) {
            stringResource(R.string.retrospect_selling_price_placeholder)
        } else {
            stringResource(R.string.retrospect_buy_price_placeholder)
        },
        value = TextFieldValue(
            text = state.value.text,
            selection = TextRange(state.value.selection)
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
                    onUpdateCurrency(if (selectedIndex == 0) CurrencyType.KRW else CurrencyType.USD)
                }
            )
        },
        visualTransformation = currentVisualTransformation
    )
}

@Composable
private fun StockTextField(
    state: State<TextFieldState>,
    modifier: Modifier = Modifier,
    onUpdateStockText: (String, Int) -> Unit,
    onFocusChanged: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    SimpleNumberTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        value = TextFieldValue(
            text = state.value.text,
            selection = TextRange(state.value.selection)
        ),
        onValueChange = { newValue ->
            val digitsOnlyText = newValue.text.filter { it.isDigit() }
            val newCursorPosition = newValue.selection.start.let { transformedOffset ->
                newValue.text.substring(0, transformedOffset).count { it.isDigit() }
            }.coerceIn(0, digitsOnlyText.length)

            onUpdateStockText(digitsOnlyText, newCursorPosition)
        },
        label = state.value.label,
        placeholder = stringResource(id = R.string.retrospect_unit_stock),
        visualTransformation = UnitVisualTransformation(unit = stringResource(id = R.string.retrospect_unit_stock)),
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
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (TextFieldValue) -> Unit,
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
    state: State<TextFieldState>,
    modifier: Modifier = Modifier,
    onUpdateDateText: (String, Int) -> Unit,
    onErrorDateText: (String, Int) -> Unit,
    onFocusChanged: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var isShowDatePicker by remember { mutableStateOf(false) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
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
        value = TextFieldValue(
            text = state.value.text,
            selection = TextRange(state.value.selection)
        ),
        onValueChange = {},
        label = state.value.label,
        placeholder = stringResource(id = R.string.retrospect_transaction_date),
        isError = state.value.isError,
        readOnly = true
    )
}

@Composable
fun ReturnTextField(
    state: State<TextFieldState>,
    returnSignTypeState: State<ReturnSignType>,
    modifier: Modifier = Modifier,
    onUpdateReturnText: (String, Int) -> Unit,
    onUpdateReturnSign: (ReturnSignType) -> Unit,
    onChangedKeyboardVisibility: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    var selectedIndex by remember {
        mutableIntStateOf(if (returnSignTypeState.value == ReturnSignType.Plus) 0 else 1)
    }
    var unitTransformation by remember(selectedIndex) {
        mutableStateOf(
            UnitVisualTransformation(
                prefix = if (selectedIndex == 0) {
                    context.getString(R.string.retrospect_unit_plus)
                } else {
                    context.getString(R.string.retrospect_unit_minus)
                },
                unit = context.getString(R.string.retrospect_unit_percent)
            )
        )
    }

    HedgeUnitTextField(
        modifier = Modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged { state ->
                if (state.isFocused) {
                    scope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            }
            .focusRequester(focusRequester)
            .padding(start = 20.dp, end = 20.dp, top = 12.dp),
        label = state.value.label,
        placeholder = stringResource(id = R.string.retrospect_unit_percent),
        value = TextFieldValue(
            text = state.value.text,
            selection = TextRange(state.value.selection)
        ),
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

                    if (selectedIndex == 0) {
                        onUpdateReturnSign(ReturnSignType.Plus)
                    } else {
                        onUpdateReturnSign(ReturnSignType.Minus)
                    }
                }
            )
        },
        visualTransformation = unitTransformation
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

private fun getTextFieldState(
    label: String = "",
    text: String = "",
    selection: Int = 0,
    isError: Boolean = false
) = mutableStateOf(
    TextFieldState.EMPTY.copy(
        label = label,
        text = text,
        selection = selection,
        isError = isError
    )
)

@Preview
@Composable
private fun RetrospectRoutePreview() {
    val retrospectionState = rememberRetrospectionState(
        requestParams = CreateRetrospectionRequest.EMPTY
    )
    val isButtonEnabled by remember {
        derivedStateOf {
            arrayOf(
                retrospectionState.sellingTextFieldState.value,
                retrospectionState.stockTextFieldState.value,
                retrospectionState.dateTextFieldState.value
            )
                .all { it.text.isNotEmpty() && !it.isError }
                .takeIf { it }
                ?.let {
                    when (retrospectionState.returnToggleState.value) {
                        true -> retrospectionState.returnTextFieldState.value.text.isNotEmpty() &&
                            !retrospectionState.returnTextFieldState.value.isError

                        false -> true
                    }
                } ?: false
        }
    }

    Scaffold { paddingValues ->
        RetrospectScreen(
            modifier = Modifier.padding(paddingValues),
            retrospectionState = retrospectionState,
            buttonEnabled = isButtonEnabled,
            companyLogoUrl = null,
            requestParams = CreateRetrospectionRequest.EMPTY.copy(orderType = OrderType.SELL),
            onUpdateSellingText = { text, selection ->
                retrospectionState.sellingTextFieldState.value =
                    retrospectionState.sellingTextFieldState.value.copy(
                        text = text,
                        selection = selection
                    )
            },
            onUpdateStockText = { text, selection ->
                retrospectionState.stockTextFieldState.value =
                    retrospectionState.stockTextFieldState.value.copy(
                        text = text,
                        selection = selection
                    )
            },
            onUpdateDateText = { text, selection ->
                retrospectionState.dateTextFieldState.value =
                    retrospectionState.dateTextFieldState.value.copy(
                        text = text,
                        selection = selection,
                        isError = false
                    )
            },
            onUpdateReturnText = { text, selection ->
                retrospectionState.returnTextFieldState.value =
                    retrospectionState.returnTextFieldState.value.copy(
                        text = text,
                        selection = selection
                    )
            },
            onErrorDateText = { text, selection ->
                retrospectionState.dateTextFieldState.value =
                    retrospectionState.dateTextFieldState.value.copy(
                        text = text,
                        selection = selection,
                        isError = true
                    )
            },
            onClickedConfirmButton = {},
            onUpdateReturnToggle = { retrospectionState.returnToggleState.value = it },
            onUpdateCurrency = { retrospectionState.currencyType.value = it },
            onUpdateReturnSign = { retrospectionState.returnSignType.value = it },
            onBackPressed = {}
        )
    }
}
