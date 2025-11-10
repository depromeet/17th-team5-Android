package com.depromeet.team5.core.domain.model

import com.depromeet.team5.core.domain.monad.HedgeUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


data class BaseDomain<T>(
    val code: String,
    val message: String,
    val data: T?
)

inline fun <reified T> BaseDomain<T>.toUiState(
    successPrefix: String = "RS_"
): HedgeUiState<T> {
    return if (code.startsWith(successPrefix)) {
        when {
            data != null -> HedgeUiState.Success(data)
            T::class == Unit::class -> HedgeUiState.Success(Unit as T)
            else -> HedgeUiState.Error(
                code = "CLIENT_NULL_DATA",
                message = "Success code but data was null"
            )
        }
    } else {
        HedgeUiState.Error(
            code = code,
            message = message
        )
    }
}

inline fun <reified T> Flow<BaseDomain<T>>.toUiStateFlow(
    successPrefix: String = "RS_"
): Flow<HedgeUiState<T>> =
    this
        .map { base -> base.toUiState<T>(successPrefix) }
        .onStart { emit(HedgeUiState.Loading()) }
        .catch { emit(HedgeUiState.Error(throwable = it)) }