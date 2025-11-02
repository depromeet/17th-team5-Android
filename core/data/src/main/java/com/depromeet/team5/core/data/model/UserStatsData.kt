package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.UserStats
import com.depromeet.team5.core.domain.model.UserStatsInfo

data class UserStatsData(
    val code: String,
    val message: String,
    val data: UserStatsInfoData,
) : DataMapper<UserStats> {
    override fun toDomain(): UserStats = UserStats(
        code = code,
        message = message,
        data = data.toDomain()
    )
}

data class UserStatsInfoData(
    val hedge: Int,
    val bronze: Int,
    val silver: Int,
    val gold: Int,
    val percentage: Int,
) : DataMapper<UserStatsInfo> {
    override fun toDomain(): UserStatsInfo = UserStatsInfo(
        hedge = hedge,
        bronze = bronze,
        silver = silver,
        gold = gold,
        percentage = percentage
    )
}