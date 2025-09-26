package com.depromeet.team5.features.feedback.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.usecase.CreateFeedbackUseCase
import com.depromeet.team5.core.model.Feedback
import com.depromeet.team5.core.model.mapper.toUi
import com.depromeet.team5.features.feedback.AiFeedbackState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class AiFeedbackViewModel @Inject constructor(
    private val createFeedbackUseCase: CreateFeedbackUseCase
) : ViewModel() {

    val feedbackStateFlow: StateFlow<AiFeedbackState> = createFeedbackUseCase(1, mapOf())
        .map { it.toUi() }
        .map {
            if (it != Feedback.EMPTY) {
                AiFeedbackState.Success(
                    summarize = it.summarize,
                    summarizeOfMarket = it.summarizeOfMarket,
                    principles = it.principles
                )
            } else {
                AiFeedbackState.Error(
                    code = it.code,
                    message = it.message
                )
            }
        }
        .catch { emit(AiFeedbackState.Failure(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AiFeedbackState.Loading
        )
}
