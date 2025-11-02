package com.depromeet.team5.core.domain.model

data class UserStats(
    val code: String,
    val message: String,
    val data: UserStatsInfo,
)

data class UserStatsInfo(
    val hedge: Int,
    val bronze: Int,
    val silver: Int,
    val gold: Int,
    val percentage: Int,
)