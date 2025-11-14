package com.depromeet.team5.feature.reasons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.depromeet.team5.core.domain.model.toUiStateFlow
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.domain.usecase.CreateMemoUseCase
import com.depromeet.team5.core.domain.usecase.DeleteMemoUseCase
import com.depromeet.team5.core.domain.usecase.DeleteRetrospectionUseCase
import com.depromeet.team5.core.domain.usecase.GetRetrospectionUseCase
import com.depromeet.team5.core.domain.usecase.ParseArticleUseCase
import com.depromeet.team5.core.domain.usecase.UpdateMemoUseCase
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.feature.reasons.model.UiRetrospection
import com.depromeet.team5.feature.reasons.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RetrospectionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRetrospectionUseCase: GetRetrospectionUseCase,
    parseArticleUseCase: ParseArticleUseCase,
    private val deleteRetrospectionUseCase: DeleteRetrospectionUseCase,
    private val createMemoUseCase: CreateMemoUseCase,
    private val updateMemoUseCase: UpdateMemoUseCase,
    private val deleteMemoUseCase: DeleteMemoUseCase,
) : ViewModel() {

    val retrospectionId = savedStateHandle.toRoute<RetrospectionDetail>().retrospectionId

    val uiState by hedgeState<HedgeUiState<UiRetrospection>>(HedgeUiState.Loading())

    init {
        getRetrospectionUseCase(retrospectionId)
            .toUiStateFlow()
            .onEach { state ->
                val result = when (state) {
                    is HedgeUiState.Success -> {
                        runCatching {
                            state.data.toUi(parseArticleUseCase::invoke)
                        }.fold(
                            onSuccess = {
                                HedgeUiState.Success(it)
                            },
                            onFailure = {
                                HedgeUiState.Error(
                                    code = "CLIENT_NULL_DATA",
                                    message = it.message,
                                    throwable = it
                                )
                            }
                        )
                    }
                    is HedgeUiState.Error -> state
                    is HedgeUiState.Loading -> HedgeUiState.Loading(null)
                }
                uiState.emit(result)
            }
            .launchIn(viewModelScope)
    }

    fun deleteRetrospection(
        onDeleteRetrospection: (Throwable?) -> Unit,
    ) {
        deleteRetrospectionUseCase(retrospectionId)
            .toUiStateFlow()
            .onEach {
                if (it is HedgeUiState.Success) {
                    onDeleteRetrospection(null)
                } else {
                    onDeleteRetrospection(Throwable(it.toString()))
                }
            }
            .launchIn(viewModelScope)
    }

    fun onRemoveMemo(memoId: Int) {
        deleteMemoUseCase(retrospectionId, memoId)
            .toUiStateFlow()
            .onEach {
                if (it is HedgeUiState.Success) {
                    uiState.update { oldState ->
                        when (oldState) {
                            is HedgeUiState.Success -> {
                                val current = oldState.data
                                val updated = current.copy(
                                    memos = current.memos.filter { memo -> memo.memoId != memoId }
                                )
                                HedgeUiState.Success(updated)
                            }

                            else -> oldState
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onUpdateMemo(
        memoId: Int,
        content: String
    ) {
        updateMemoUseCase(retrospectionId, memoId, content)
            .toUiStateFlow()
            .onEach {
                if (it is HedgeUiState.Success) {
                    uiState.update { oldState ->
                        when (oldState) {
                            is HedgeUiState.Success -> {
                                val current = oldState.data
                                val updated = current.copy(
                                    memos = current.memos.map {
                                        if (it.memoId == memoId) it.copy(content = content) else it
                                    }
                                )
                                HedgeUiState.Success(updated)
                            }

                            else -> oldState
                        }
                    }
                }
            }
            .launchIn(viewModelScope)

    }


    fun onCreateMemo(content: String) {
        createMemoUseCase(retrospectionId, content)
            .toUiStateFlow()
            .onEach {
                if (it is HedgeUiState.Success) {
                    uiState.update { oldState ->
                        when (oldState) {
                            is HedgeUiState.Success -> {
                                val current = oldState.data
                                val updated = current.copy(
                                    memos = listOf(it.data) + current.memos
                                )
                                HedgeUiState.Success(updated)
                            }

                            else -> oldState
                        }
                    }
                }
            }
            .launchIn(viewModelScope)

    }


    companion object {
        const val INVALID_ID = -999
        const val ADD_MEMO_ID = -1
    }
}