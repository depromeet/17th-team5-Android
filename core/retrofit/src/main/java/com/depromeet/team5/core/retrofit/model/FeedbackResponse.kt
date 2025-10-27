package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.FeedbackInfoRemoteData
import com.depromeet.team5.core.remotedatasource.model.FeedbackRemoteData
import com.depromeet.team5.core.remotedatasource.model.PrincipleCheckSummaryRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.SerialName
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
    @SerialName("뱃지")
    val badge: String,
    val symbol: String,
    val orderType: String,
    @SerialName("volume")
    val volume: Int,
    val price: Long,
    val principleCheckSummary: PrincipleCheckSummaryResponse,
    @SerialName("앞으로도 유지해보세요")
    val keep: List<String>,
    @SerialName("고쳐보면 좋아요")
    val fix: List<String>,
    @SerialName("다음 투자엔 이렇게 해보세요")
    val next: List<String>,
) : RetrofitMapper<FeedbackInfoRemoteData> {
    override fun toRemoteData(): FeedbackInfoRemoteData =
        FeedbackInfoRemoteData(
            badge = badge,
            symbol = symbol,
            orderType = orderType,
            volume = volume,
            price = price,
            principleCheckSummary = principleCheckSummary.toRemoteData(),
            keep = keep,
            fix = fix,
            next = next
        )
}

@Serializable
data class PrincipleCheckSummaryResponse(
    val keptCount: Int,
    val neutralCount: Int,
    val notKeptCount: Int,
) : RetrofitMapper<PrincipleCheckSummaryRemoteData> {
    override fun toRemoteData(): PrincipleCheckSummaryRemoteData =
        PrincipleCheckSummaryRemoteData(
            keptCount = keptCount,
            neutralCount = neutralCount,
            notKeptCount = notKeptCount
        )
}
