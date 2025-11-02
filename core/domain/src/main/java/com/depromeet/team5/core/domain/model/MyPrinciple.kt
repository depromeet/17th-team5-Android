package com.depromeet.team5.core.domain.model

import androidx.compose.runtime.Immutable


@Immutable
data class MyPrinciple(
    val id: Int,
    val groupId: Int,
    val principle: String,
    val description: String
)
