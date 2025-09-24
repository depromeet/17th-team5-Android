package com.depromeet.team5.features.retrospect.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.features.retrospect.annotation.DATE
import com.depromeet.team5.features.retrospect.annotation.RETURN
import com.depromeet.team5.features.retrospect.annotation.SELLING
import com.depromeet.team5.features.retrospect.annotation.STOCK
import com.depromeet.team5.features.retrospect.annotation.savedStateHandleKey
import com.depromeet.team5.features.retrospect.extensions.HedgeState
import com.depromeet.team5.features.retrospect.extensions.baseCollect
import com.depromeet.team5.features.retrospect.extensions.hedgeState
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

    val sellingTextFieldState: HedgeState<TextFieldState> by hedgeState {
        TextFieldState.EMPTY.copy(text = savedStateHandle[SELLING] ?: "")
    }

    val stockTextFieldState: HedgeState<TextFieldState> by hedgeState {
        TextFieldState.EMPTY.copy(text = savedStateHandle[STOCK] ?: "")
    }
    val dateTextFieldState: HedgeState<TextFieldState> by hedgeState {
        TextFieldState.EMPTY.copy(text = savedStateHandle[DATE] ?: "")
    }
    val returnTextFieldState: HedgeState<TextFieldState> by hedgeState {
        TextFieldState.EMPTY.copy(text = savedStateHandle[RETURN] ?: "")
    }

    val returnToggleState by hedgeState { false }

    val okButtonState: HedgeState<Boolean> by hedgeState { false }


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
        key: savedStateHandleKey,
        value: String,
        selection: Int,
        isError: Boolean = false
    ) {
        when (key) {
            SELLING -> sellingTextFieldState.update {
                it.copy(text = value, selection = selection, isError = isError)
            }
            STOCK -> stockTextFieldState.update {
                it.copy(text = value, selection = selection, isError = isError)
            }
            DATE -> dateTextFieldState.update {
                it.copy(text = value, selection = selection, isError = isError)
            }
            RETURN -> returnTextFieldState.update {
                it.copy(text = value, selection = selection, isError = isError)
            }
        }

        savedStateHandle[key] = value
    }


    fun onClickedConfirmButton() {
        if (!okButtonState.stateFlow.value) return

        //todo 서버에 값 전달하는 코드 추가하기
    }
}
