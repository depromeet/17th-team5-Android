package com.depromeet.team5.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx(): Float = LocalDensity.current.run { return toPx() }

@Composable
fun Float.toDp(): Dp = LocalDensity.current.run { return this@toDp.toDp() }

@Composable
fun Int.toDp(): Dp = LocalDensity.current.run { return this@toDp.toDp() }