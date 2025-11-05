package com.depromeet.team5.feature.reasons.model

import androidx.compose.runtime.Immutable

@Immutable
data class Article(
    val originUrl: String,
    val title: String?,
    val thumbnail: String?,
    val source: String?
)