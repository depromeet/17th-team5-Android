package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.FeedbackData
import com.depromeet.team5.core.data.model.FeedbackInfoData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

data class FeedbackRemoteData(
    val code: String,
    val message: String,
    val data: FeedbackInfoRemoteData?,
) : RemoteDataMapper<FeedbackData> {
    override fun toData(): FeedbackData =
        FeedbackData(
            code = code,
            message = message,
            data = data?.toData()
        )
}

data class FeedbackInfoRemoteData(
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
) : RemoteDataMapper<FeedbackInfoData> {
    override fun toData(): FeedbackInfoData =
        FeedbackInfoData(
            badge = badge,
            companyName = companyName,
            companyLogo = companyLogo,
            orderType = orderType,
            volume = volume,
            price = price,
            keptCount = keptCount,
            neutralCount = neutralCount,
            notKeptCount = notKeptCount,
            keep = keep,
            fix = fix,
            next = next
        )
}
