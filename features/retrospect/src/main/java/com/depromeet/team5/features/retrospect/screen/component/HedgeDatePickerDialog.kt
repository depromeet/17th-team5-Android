package com.depromeet.team5.features.retrospect.screen.component

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.depromeet.team5.core.designsystem.foundation.HedgeColor
import com.depromeet.team5.core.ui.R as UiR


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HedgeDatePickerDialog(
    datePickerState: DatePickerState = rememberDatePickerState(),
    onDismissRequest: () -> Unit,
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit
) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = HedgeColor.Transparent,
                    contentColor = HedgeColor.GREY_900
                ),
                onClick = onClickConfirm
            ) {
                Text(text = stringResource(UiR.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = HedgeColor.Transparent,
                    contentColor = HedgeColor.GREY_900
                ),
                onClick = onClickDismiss
            ) {
                Text(text = stringResource(UiR.string.cancel))
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = HedgeColor.WHITE
        )
    ) {
        DatePicker(
            datePickerState,
            headline = null,
            title = null,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = HedgeColor.WHITE,
                titleContentColor = HedgeColor.GREY_900,
                headlineContentColor = HedgeColor.GREY_900,
                weekdayContentColor = HedgeColor.GREY_900,
                subheadContentColor = HedgeColor.GREY_900,
                navigationContentColor = HedgeColor.GREY_900,
                yearContentColor = HedgeColor.GREY_900,
                disabledYearContentColor = HedgeColor.GREY_500,
                currentYearContentColor = HedgeColor.GREY_900,
                selectedYearContentColor = HedgeColor.WHITE,
                selectedYearContainerColor = HedgeColor.Brand.Primary,
                disabledSelectedYearContentColor = HedgeColor.WHITE,
                disabledSelectedYearContainerColor = HedgeColor.RED_500,
                dayContentColor = HedgeColor.GREY_900,
                disabledDayContentColor = HedgeColor.GREY_500,
                disabledSelectedDayContentColor = HedgeColor.GREY_500,
                disabledSelectedDayContainerColor = HedgeColor.RED_500,
                selectedDayContentColor = HedgeColor.WHITE,
                selectedDayContainerColor = HedgeColor.Brand.Primary,
                todayContentColor = HedgeColor.GREY_900,
                dateTextFieldColors = TextFieldDefaults.colors(
                    focusedContainerColor = HedgeColor.RED_500
                )
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HedgeDatePickerPreview() {
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    HedgeDatePickerDialog(
        onDismissRequest = { showDatePicker = false },
        onClickConfirm = {
            selectedDate = datePickerState.selectedDateMillis
            showDatePicker = false
        },
        onClickDismiss = {
            showDatePicker = false
        }
    )
}