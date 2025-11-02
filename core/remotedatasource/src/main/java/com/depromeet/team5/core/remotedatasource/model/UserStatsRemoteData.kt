package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.UserStatsData
import com.depromeet.team5.core.data.model.UserStatsInfoData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

data class UserStatsRemoteData(
    val code: String,
    val message: String,
    val data: UserStatsInfoRemoteData,
) : RemoteDataMapper<UserStatsData> {
    override fun toData(): UserStatsData = UserStatsData(
        code = code,
        message = message,
        data = data.toData()
    )
}

data class UserStatsInfoRemoteData(
    val hedge: Int,
    val bronze: Int,
    val silver: Int,
    val gold: Int,
    val percentage: Int,
) : RemoteDataMapper<UserStatsInfoData> {
    override fun toData(): UserStatsInfoData = UserStatsInfoData(
        hedge = hedge,
        bronze = bronze,
        silver = silver,
        gold = gold,
        percentage = percentage
    )
}