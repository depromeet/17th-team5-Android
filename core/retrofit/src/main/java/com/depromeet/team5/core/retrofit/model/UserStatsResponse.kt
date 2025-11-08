package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.UserStatsInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.UserStatsRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.Serializable

@Serializable
data class UserStatsResponse(
    val code: String,
    val message: String,
    val data: UserStatsInfoResponse,
) : RetrofitMapper<UserStatsRemoteData> {
    override fun toRemoteData(): UserStatsRemoteData = UserStatsRemoteData(
        code = code,
        message = message,
        data = data.toRemoteData()
    )
}

@Serializable
data class UserStatsInfoResponse(
    val hedge: Int,
    val bronze: Int,
    val silver: Int,
    val gold: Int,
    val percentage: Int,
) : RetrofitMapper<UserStatsInfoRemoteData> {
    override fun toRemoteData(): UserStatsInfoRemoteData = UserStatsInfoRemoteData(
        hedge = hedge,
        bronze = bronze,
        silver = silver,
        gold = gold,
        percentage = percentage
    )
}