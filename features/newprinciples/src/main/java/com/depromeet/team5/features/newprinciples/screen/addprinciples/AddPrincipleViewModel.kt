package com.depromeet.team5.features.newprinciples.screen.addprinciples

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.monad.BaseEvent
import com.depromeet.team5.core.domain.usecase.AddPrincipleUseCase
import com.depromeet.team5.core.domain.usecase.GetPrincipleGroupUseCase
import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.features.newprinciples.screen.navigation.AddPrinciplesRoute
import com.depromeet.team5.features.newprinciples.screen.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddPrincipleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPrincipleGroupUseCase: GetPrincipleGroupUseCase,
    private val addPrincipleUseCase: AddPrincipleUseCase
) : ViewModel() {

    private val route = savedStateHandle.toRoute<AddPrinciplesRoute>()

    private val titleList = MutableList(route.principles.size) { "" }

    val uiState by hedgeState(UiState.EMPTY)

    private val _event = MutableSharedFlow<BaseEvent<Unit>>()
    val event = _event.asSharedFlow()


    init {
        viewModelScope.launch {
            combine(
                getGroupName(),
                getNewPrinciples()
            ) { groupName, newPrinciples -> groupName to newPrinciples }
                .baseCollect(
                    onSuccess = { (groupName, newPrinciples) ->
                        uiState.update {
                            it.copy(
                                groupName = groupName,
                                newPrinciples = newPrinciples,
                                isLastPage = it.page + 1 == newPrinciples.size
                            )
                        }
                    },
                    onError = {
                        _event.emit(BaseEvent.Error(it))
                    }
                )
        }
    }

    private fun getGroupName() = flow {
        emit(route.groupId)
    }
        .flatMapLatest { getPrincipleGroupUseCase(it) }
        .map { it.groupName }

    private fun getNewPrinciples() = flow {
        emit(route.principles)
    }

    fun updateTitle(page: Int, title: String) {
        titleList[page] = title
        uiState.update { it.copy(title = titleList[page]) }
    }

    fun nextPage() {
        val nextPage = uiState.value.page + 1

        uiState.update {
            it.copy(
                page = nextPage,
                title = "",
                isLastPage = nextPage == it.newPrinciples.size - 1
            )
        }
    }

    fun complete() {
        viewModelScope.launch {
            titleList
                .mapIndexed { index, title -> title to route.principles[index] }
                .asFlow()
                .flatMapConcat { (principle, description) ->
                    addPrincipleUseCase(
                        groupId = route.groupId,
                        principle = principle,
                        description = description
                    )
                }
                .baseCollect(
                    onSuccess = {
                        _event.emit(BaseEvent.Finish(Unit))
                    },
                    onError = {
                        _event.emit(BaseEvent.Error(it))
                    }
                )
        }
    }
}
