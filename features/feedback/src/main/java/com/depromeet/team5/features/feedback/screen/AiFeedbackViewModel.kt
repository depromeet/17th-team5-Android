package com.depromeet.team5.features.feedback.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.usecase.CreateFeedbackUseCase
import com.depromeet.team5.core.model.Feedback
import com.depromeet.team5.core.model.mapper.toUi
import com.depromeet.team5.features.feedback.AiFeedbackState
import com.depromeet.team5.features.feedback.navigation.FeedbackParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class AiFeedbackViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createFeedbackUseCase: CreateFeedbackUseCase
) : ViewModel() {

    val feedbackParams = savedStateHandle.toRoute<FeedbackParams>()

    private val _companyName = MutableStateFlow(feedbackParams.companyName)
    val companyName: StateFlow<String> = _companyName.asStateFlow()

    private val _price = MutableStateFlow(feedbackParams.price)
    val price: StateFlow<Long> = _price.asStateFlow()

    private val _stock = MutableStateFlow(feedbackParams.stock)
    val stock: StateFlow<Int> = _stock.asStateFlow()

    private val _date = MutableStateFlow(feedbackParams.date)
    val date: StateFlow<String> = _date.asStateFlow()

    val feedbackStateFlow: StateFlow<AiFeedbackState> =
        createFeedbackUseCase(feedbackParams.retrospectionId, mapOf())
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
