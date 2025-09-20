package com.depromeet.team5.features.retrospect.screen

import androidx.lifecycle.ViewModel
import com.depromeet.team5.features.retrospect.screen.extensions.HedgeState
import com.depromeet.team5.features.retrospect.screen.extensions.hedgeState
import com.depromeet.team5.features.retrospect.screen.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RetrospectViewModel @Inject constructor() : ViewModel() {

    val sellingTextFieldState: HedgeState<TextFieldState> by hedgeState {
        TextFieldState.EMPTY
    }

    val stockTextFieldState: HedgeState<TextFieldState> by hedgeState {
        TextFieldState.EMPTY
    }

    val dateTextFieldState: HedgeState<TextFieldState> by hedgeState {
        TextFieldState.EMPTY
    }

    val returnTextFieldState: HedgeState<TextFieldState> by hedgeState {
        TextFieldState.EMPTY
    }

}
