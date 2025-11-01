package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.FeedbackInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackResponse(
    val code: String,
    val message: String,
    val data: FeedbackInfoResponse?,
) : RetrofitMapper<FeedbackRemoteData> {
    override fun toRemoteData(): FeedbackRemoteData =
        FeedbackRemoteData(
            code = code,
            message = message,
            data = data?.toRemoteData()
        )
}

@Serializable
data class FeedbackInfoResponse(
    val symbol: String,
    val price: Long,
    val volume: Int,
    val orderType: String,
    val keptCount: Int,
    val neutralCount: Int,
    val notKeptCount: Int,
    val badge: String,
    val keep: List<String>,
    val fix: List<String>,
    val next: List<String>,
) : RetrofitMapper<FeedbackInfoRemoteData> {
    override fun toRemoteData(): FeedbackInfoRemoteData =
        FeedbackInfoRemoteData(
            badge = badge,
            symbol = symbol,
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
