package com.depromeet.team5.features.retrospect.screen.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview


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
                onClick = onClickConfirm
            ) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = onClickDismiss) {
                Text("취소")
            }
        }
    ) {
        DatePicker(
            datePickerState,
            headline = null,
            title = null,
            showModeToggle = false
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