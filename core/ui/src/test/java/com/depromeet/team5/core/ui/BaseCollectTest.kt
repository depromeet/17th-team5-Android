package com.depromeet.team5.core.ui

import com.depromeet.team5.core.ui.extensions.baseCollect
import com.depromeet.team5.core.ui.extensions.baseCollectLatest
import com.depromeet.team5.core.ui.lazy.hedgeState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test


class BaseCollectTest {

    @Test
    fun `Flow는 baseCollect 함수로 데이터를 옳바르게 처리할 수 있다`() = runTest(UnconfinedTestDispatcher()) {
        val flow = flow {
            emit(1)
        }

        launch {
            flow.baseCollect(
                { result ->
                    assertEquals(1, result)
                }, {}
            )
        }
    }

    @Test
    fun `Flow는 baseCollect함수로 flow 함수 종료 후 action 처리할 수 있다`() =
        runTest(UnconfinedTestDispatcher()) {
            val isLoading by hedgeState(true)

            val flow = flow {
                emit(1)
            }

            launch {
                flow.baseCollect(
                    { result ->
                        assertEquals(1, result)
                    }, {}, {
                        isLoading.update { false }
                    }
                )
            }

            assertEquals(false, isLoading.value)
        }

    @Test
    fun `Flow는 baseCollect함수로 에러 핸들링을 옳바르게 처리할 수 있다`() = runTest(UnconfinedTestDispatcher()) {
        val flow = flow {
            throw NullPointerException()
            emit(1)
        }

        launch {
            flow.baseCollect(
                {}, { throwable ->
                    assertTrue(throwable is NullPointerException)
                }
            )
        }
    }

    @Test
    fun `Flow는 baseCollectLatest함수로 데이터를 옳바르게 처리할 수 있다`() = runTest(UnconfinedTestDispatcher()) {
        val flow = flow {
            emit(1)
        }

        launch {
            flow.baseCollectLatest(
                { result ->
                    assertEquals(1, result)
                }, {}
            )
        }
    }

    @Test
    fun `Flow는 baseCollectLatest함수로 flow 함수 종료 후 action 처리할 수 있다`() =
        runTest(UnconfinedTestDispatcher()) {
            val isLoading by hedgeState(true)

            val flow = flow {
                emit(1)
            }

            launch {
                flow.baseCollectLatest(
                    { result ->
                        assertEquals(1, result)
                    }, {}, {
                        isLoading.update { false }
                    }
                )
            }

            assertEquals(false, isLoading.value)
        }

    @Test
    fun `Flow는 baseCollectLatest함수로 에러 핸들링을 옳바르게 처리할 수 있다`() = runTest(UnconfinedTestDispatcher()) {
        val flow = flow {
            throw NullPointerException()
            emit(1)
        }

        launch {
            flow.baseCollectLatest(
                {}, { throwable ->
                    assertTrue(throwable is NullPointerException)
                }
            )
        }
    }

    @Test
    fun `StateFlow는 baseCollect 함수로 데이터를 옳바르게 처리할 수 있다`() = runTest(UnconfinedTestDispatcher()) {
        val flow = MutableStateFlow(1)

        launch {
            flow.baseCollect(
                { result ->
                    assertEquals(1, result)

                    cancel() // hot flow를 종료하기 위함
                }, {}
            )
        }
    }

    @Test
    fun `SharedFlow는 baseCollect 함수로 데이터를 옳바르게 처리할 수 있다`() = runTest(UnconfinedTestDispatcher()) {
        val flow = MutableSharedFlow<Int>()

        launch {
            flow.baseCollect(
                { result ->
                    assertEquals(1, result)

                    cancel() // hot flow를 종료하기 위함
                }, {}
            )
        }

        flow.emit(1)
    }
}
