package com.depromeet.team5.core.ui.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion


suspend fun <T> Flow<T>.baseCollect(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit
) = catch(onError)
    .collect(onSuccess)

suspend fun <T> Flow<T>.baseCollect(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit,
    onComplete: suspend FlowCollector<T>.(cause: Throwable?) -> Unit
) = onCompletion(onComplete)
    .baseCollect(onSuccess, onError)

suspend fun <T> Flow<T>.baseCollectLatest(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit
) = catch(onError)
    .collect(onSuccess)

suspend fun <T> Flow<T>.baseCollectLatest(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit,
    onComplete: suspend FlowCollector<T>.(cause: Throwable?) -> Unit
) = onCompletion(onComplete)
    .baseCollectLatest(onSuccess, onError)

@Deprecated(
    message = "StateFlow는 Collect가 종료되지 않아 onComplete함수가 호출되지 않습니다.",
    level = DeprecationLevel.WARNING
)
suspend fun <T> StateFlow<T>.baseCollect(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit,
    onComplete: suspend FlowCollector<T>.(cause: Throwable?) -> Unit
) = onCompletion(onComplete)
    .baseCollect(onSuccess, onError)

@Deprecated(
    message = "StateFlow는 CollectLatest가 종료되지 않아 onComplete함수가 호출되지 않습니다.",
    level = DeprecationLevel.WARNING
)
suspend fun <T> StateFlow<T>.baseCollectLatest(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit,
    onComplete: suspend FlowCollector<T>.(cause: Throwable?) -> Unit
) = onCompletion(onComplete)
    .baseCollectLatest(onSuccess, onError)

@Deprecated(
    message = "SharedFlow는 Collect가 종료되지 않아 onComplete함수가 호출되지 않습니다.",
    level = DeprecationLevel.WARNING
)
suspend fun <T> SharedFlow<T>.baseCollect(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit,
    onComplete: suspend FlowCollector<T>.(cause: Throwable?) -> Unit
) = onCompletion(onComplete)
    .baseCollect(onSuccess, onError)

@Deprecated(
    message = "SharedFlow는 CollectLatest가 종료되지 않아 onComplete함수가 호출되지 않습니다.",
    level = DeprecationLevel.WARNING
)
suspend fun <T> SharedFlow<T>.baseCollectLatest(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit,
    onComplete: suspend FlowCollector<T>.(cause: Throwable?) -> Unit
) = onCompletion(onComplete)
    .baseCollect(onSuccess, onError)
