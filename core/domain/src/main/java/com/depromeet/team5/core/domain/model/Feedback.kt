package com.depromeet.team5.core.domain.model

data class Feedback(
    val code: String,
    val message: String,
    val data: FeedbackInfo?,
)

data class FeedbackInfo(
    val badge: String,
    val symbol: String,
    val orderType: String,
    val volume: Int,
    val price: Long,
    val principleCheckSummary: PrincipleCheckSummary,
    val keep: List<String>,
    val fix: List<String>,
    val next: List<String>,
)

data class PrincipleCheckSummary(
    val keptCount: Int,
    val neutralCount: Int,
    val notKeptCount: Int,
)
