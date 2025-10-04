package com.depromeet.team5.core.ui.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
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
    onSuccess: suspend (T) -> Unit
) = collectLatest(onSuccess)

suspend fun <T> Flow<T>.baseCollectLatest(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit
) = catch(onError)
    .baseCollectLatest(onSuccess)

suspend fun <T> Flow<T>.baseCollectLatest(
    onSuccess: suspend (T) -> Unit,
    onError: suspend FlowCollector<T>.(cause: Throwable) -> Unit,
    onComplete: suspend FlowCollector<T>.(cause: Throwable?) -> Unit
) = onCompletion(onComplete)
    .baseCollectLatest(onSuccess, onError)
