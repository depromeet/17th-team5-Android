package com.depromeet.team5.features.feedback.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.usecase.CreateFeedbackUseCase
import com.depromeet.team5.core.domain.usecase.CreateRetrospectionUseCase
import com.depromeet.team5.core.model.Feedback
import com.depromeet.team5.core.model.mapper.toUi
import com.depromeet.team5.core.model.request.CreateRetrospectionParams
import com.depromeet.team5.features.feedback.AiFeedbackUiState
import com.depromeet.team5.features.feedback.PrincipleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AiFeedbackViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createRetrospectionUseCase: CreateRetrospectionUseCase,
    private val createFeedbackUseCase: CreateFeedbackUseCase
) : ViewModel() {

    private val _companyName = MutableStateFlow("")
    val companyName: StateFlow<String> = _companyName.asStateFlow()

    private val _price = MutableStateFlow(0L)
    val price: StateFlow<Long> = _price.asStateFlow()

    private val _stock = MutableStateFlow(0)
    val stock: StateFlow<Int> = _stock.asStateFlow()

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date.asStateFlow()

    private val _feedbackStateFlow: MutableStateFlow<AiFeedbackUiState> =
        MutableStateFlow(AiFeedbackUiState.Loading)
    val feedbackStateFlow: StateFlow<AiFeedbackUiState> = _feedbackStateFlow


    init {
        viewModelScope.launch {
            createFeedbackUseCase(0)
                .map { it.toUi() }
                .map {
                    if (it != Feedback.EMPTY) {
                        AiFeedbackUiState.Success(
                            summarize = it.summarize,
                            summarizeOfMarket = it.summarizeOfMarket,
                            principles = it.principles.map { principle ->
                                PrincipleState(
                                    title = principle.title,
                                    content = principle.content,
                                    isAdd = false
                                )
                            }
                        )
                    } else {
                        AiFeedbackUiState.Error(
                            code = it.code,
                            message = it.message
                        )
                    }
                }
                .catch { emit(AiFeedbackUiState.Failure(it)) }
                .collect { state ->
                    _feedbackStateFlow.emit(state)
                }
        }
    }

    fun createRetrospection(request: CreateRetrospectionParams) = createRetrospectionUseCase(
        body = mapOf(
            "symbol" to request.symbol,
            "market" to request.market,
            "orderType" to request.orderType.name,
            "price" to request.price,
            "currency" to request.currency,
            "volume" to request.volume,
            "orderDate" to request.orderDate,
            "returnRate" to request.returnRate,
            "content" to request.content,
            "principleChecks" to request.principleChecks,
            "emotion" to request.emotion?.name
        )
    )

    fun updatePrinciple(title: String) {
        when (_feedbackStateFlow.value) {
            is AiFeedbackUiState.Success -> {
                val index =
                    (_feedbackStateFlow.value as AiFeedbackUiState.Success).principles.indexOfFirst { it.title == title }

                if (index == -1) return

                val principles = (_feedbackStateFlow.value as AiFeedbackUiState.Success).principles
                val target = principles[index]

                val newPrinciple = target.copy(isAdd = !target.isAdd)

                val newPrinciples = principles.toMutableList().apply {
                    set(index, newPrinciple)
                }

                _feedbackStateFlow.update {
                    (_feedbackStateFlow.value as AiFeedbackUiState.Success).copy(
                        principles = newPrinciples
                    )
                }

            }
            else -> {}
        }
    }
}
