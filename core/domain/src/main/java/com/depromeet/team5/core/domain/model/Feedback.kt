package com.depromeet.team5.core.domain.model

data class Feedback(
    val code: String,
    val message: String,
    val data: FeedbackInfo?,
)

data class FeedbackInfo(
    val companyName: String,
    val price: Long,
    val volume: Int,
    val orderType: String,
    val companyLogo: String?,
    val keptCount: Int,
    val neutralCount: Int,
    val notKeptCount: Int,
    val badge: String,
    val keep: List<String>,
    val fix: List<String>,
    val next: List<String>,
)
