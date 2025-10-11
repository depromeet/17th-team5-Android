package com.depromeet.team5.core.ui

import com.depromeet.team5.core.ui.lazy.hedgeState
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class HedgeStateTest {

    @Test
    fun `hegeState의 값을 update 함수로 변경할 수 있다`() {
        val state by hedgeState(0)

        state.update { it + 1 }

        assertEquals(1, state.value)
    }

    @Test
    fun `hegeState의 값을 tryEmit 함수로 변경할 수 있다`() {
        val state by hedgeState(0)

        state.tryEmit(1)

        assertEquals(1, state.value)
    }

    @Test
    fun `hegeState의 값을 emit 함수로 변경할 수 있다`() = runTest(UnconfinedTestDispatcher()) {
        val state by hedgeState(0)

        state.emit(1)

        assertEquals(1, state.value)
    }
}
