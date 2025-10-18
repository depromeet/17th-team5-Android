package com.depromeet.team5.features.retrospect.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.lazy.HedgeState
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.retrospect.annotation.CurrencyType
import com.depromeet.team5.features.retrospect.annotation.ReturnSignType
import com.depromeet.team5.features.retrospect.annotation.TextFieldType
import com.depromeet.team5.features.retrospect.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RetrospectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val sellingTextFieldState: HedgeState<TextFieldState> by hedgeState(
        TextFieldState.EMPTY.copy(
            text = savedStateHandle[TextFieldType.from(TextFieldType.Selling)] ?: ""
        )
    )

    val stockTextFieldState: HedgeState<TextFieldState> by hedgeState(
        TextFieldState.EMPTY.copy(
            text = savedStateHandle[TextFieldType.from(TextFieldType.Stock)] ?: ""
        )
    )
    val dateTextFieldState: HedgeState<TextFieldState> by hedgeState(
        TextFieldState.EMPTY.copy(
            text = savedStateHandle[TextFieldType.from(TextFieldType.Date)] ?: ""
        )
    )
    val returnTextFieldState: HedgeState<TextFieldState> by hedgeState(
        TextFieldState.EMPTY.copy(
            text = savedStateHandle[TextFieldType.from(TextFieldType.Return)] ?: ""
        )
    )

    val currencyState by hedgeState<CurrencyType>(CurrencyType.KRW)

    val returnSignState by hedgeState<ReturnSignType>(ReturnSignType.Plus)

    val returnToggleState by hedgeState(false)

    val okButtonState: HedgeState<Boolean> by hedgeState(false)


    init {
        viewModelScope.launch {
            combine(
                sellingTextFieldState.stateFlow,
                stockTextFieldState.stateFlow,
                dateTextFieldState.stateFlow
            ) { array -> array.all { it.text.isNotEmpty() && !it.isError } }
                .map { isResult ->
                    if (returnToggleState.stateFlow.value) {
                        val textIsNotEmpty = returnTextFieldState.stateFlow.value.text.isNotEmpty()
                        val isError = returnTextFieldState.stateFlow.value.isError

                        isResult && textIsNotEmpty && !isError
                    } else {
                        isResult
                    }
                }
                .baseCollect(
                    onSuccess = { isResult ->
                        okButtonState.update { isResult }
                    },
                    onError = {
                        //todo 추후에 에러처리하기
                    }
                )
        }
    }

    fun updateState(
        type: TextFieldType,
        value: String,
        selection: Int,
        isError: Boolean = false
    ) {
        when (type) {
            TextFieldType.Selling -> sellingTextFieldState.update {
                it.copy(text = value, selection = selection, isError = isError)
            }

            TextFieldType.Stock -> stockTextFieldState.update {
                it.copy(text = value, selection = selection, isError = isError)
            }

            TextFieldType.Date -> dateTextFieldState.update {
                it.copy(text = value, selection = selection, isError = isError)
            }

            TextFieldType.Return -> returnTextFieldState.update {
                it.copy(text = value, selection = selection, isError = isError)
            }
        }

        savedStateHandle[TextFieldType.from(type)] = value
    }
}
