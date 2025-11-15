package com.depromeet.team5.features.feedback.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.model.PrincipleGroupState
import com.depromeet.team5.core.domain.request.CreateRetrospectionRequest
import com.depromeet.team5.core.domain.usecase.CreateFeedbackUseCase
import com.depromeet.team5.core.domain.usecase.CreateRetrospectionUseCase
import com.depromeet.team5.core.domain.usecase.GetFeedbackUseCase
import com.depromeet.team5.features.feedback.AiFeedbackUiState
import com.depromeet.team5.features.feedback.PrincipleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.depromeet.team5.core.logger.Logger
import javax.inject.Inject

@HiltViewModel
class AiFeedbackViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createRetrospectionUseCase: CreateRetrospectionUseCase,
    private val createFeedbackUseCase: CreateFeedbackUseCase,
    private val getFeedbackUseCase: GetFeedbackUseCase,
    private val logger: Logger
) : ViewModel() {

    private val _feedbackStateFlow: MutableStateFlow<AiFeedbackUiState> =
        MutableStateFlow(AiFeedbackUiState.Loading)
    val feedbackStateFlow: StateFlow<AiFeedbackUiState> = _feedbackStateFlow

    fun createRetrospection(
        request: CreateRetrospectionRequest,
        principleGroupState: PrincipleGroupState,
    ) {
        viewModelScope.launch {
            var createdId = -1

            createRetrospectionUseCase(
                request = request.copy(orderDate = formatDate(request.orderDate)),
                principles = principleGroupState.principles,
            )
                .onEach { createdId = it.data!!.id }
                .flatMapConcat { createFeedbackUseCase(it.data!!.id) }
                .map {
                    if (it.data != null) {
                        AiFeedbackUiState.Success(
                            retrospectionId = createdId,
                            companyName = it.data!!.companyName,
                            companyLogo = it.data!!.companyLogo,
                            price = it.data!!.price,
                            volume = it.data!!.volume,
                            orderType = it.data!!.orderType,
                            badge = it.data!!.badge,
                            principleCheckSummary = PrincipleState(
                                keptCount = it.data!!.keptCount,
                                neutralCount = it.data!!.neutralCount,
                                notKeptCount = it.data!!.notKeptCount
                            ),
                            keep = it.data!!.keep,
                            fix = it.data!!.fix,
                            next = it.data!!.next
                        )
                    } else {
                        AiFeedbackUiState.Error(
                            code = it.code,
                            message = it.message
                        )
                    }
                }
                .catch {
                    logger.e(it)
                    emit(AiFeedbackUiState.Failure(it))
                }
                .onEach { _feedbackStateFlow.value = it }
                .collect()
        }
    }

    fun loadFeedback(retrospectionId: Int) {
        viewModelScope.launch {
            getFeedbackUseCase(retrospectionId)
                .map{
                    if (it.data != null) {
                        AiFeedbackUiState.Success(
                            retrospectionId = retrospectionId,
                            companyName = it.data!!.companyName,
                            companyLogo = it.data!!.companyLogo,
                            price = it.data!!.price,
                            volume = it.data!!.volume,
                            orderType = it.data!!.orderType,
                            badge = it.data!!.badge,
                            principleCheckSummary = PrincipleState(
                                keptCount = it.data!!.keptCount,
                                neutralCount = it.data!!.neutralCount,
                                notKeptCount = it.data!!.notKeptCount
                            ),
                            keep = it.data!!.keep,
                            fix = it.data!!.fix,
                            next = it.data!!.next
                        )
                    } else {
                        AiFeedbackUiState.Error(
                            code = it.code,
                            message = it.message
                        )
                    }
                }
                .catch {
                    logger.e(it)
                    emit(AiFeedbackUiState.Failure(it))
                }
                .onEach { _feedbackStateFlow.value = it }
                .collect()
        }

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
}
