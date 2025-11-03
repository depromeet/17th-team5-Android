package com.depromeet.team5.core.domain.model

import androidx.compose.runtime.Immutable


@Immutable
data class MyPrincipleGroup(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val displayOrder: Int,
    val principles: List<MyPrinciple>
)

@Immutable
data class MyPrinciple(
    val id: Int,
    val groupId: Int,
    val groupName: String,
    val principle: String,
    val description: String,
    val status: String = "",
)
