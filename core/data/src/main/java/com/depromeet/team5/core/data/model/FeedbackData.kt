package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.Feedback
import com.depromeet.team5.core.domain.model.FeedbackInfo
import com.depromeet.team5.core.domain.model.PrincipleCheckSummary

data class FeedbackData(
    val code: String,
    val message: String,
    val data: FeedbackInfoData?,
) : DataMapper<Feedback> {
    override fun toDomain(): Feedback =
        Feedback(
            code = code,
            message = message,
            data = data?.toDomain()
        )
}

data class FeedbackInfoData(
    val badge: String,
    val symbol: String,
    val orderType: String,
    val volume: Int,
    val price: Long,
    val principleCheckSummary: PrincipleCheckSummaryData,
    val keep: List<String>,
    val fix: List<String>,
    val next: List<String>,
) : DataMapper<FeedbackInfo> {
    override fun toDomain(): FeedbackInfo =
        FeedbackInfo(
            badge = badge,
            symbol = symbol,
            orderType = orderType,
            volume = volume,
            price = price,
            principleCheckSummary = principleCheckSummary.toDomain(),
            keep = keep,
            fix = fix,
            next = next
        )
}

data class PrincipleCheckSummaryData(
    val keptCount: Int,
    val neutralCount: Int,
    val notKeptCount: Int,
) : DataMapper<PrincipleCheckSummary> {
    override fun toDomain(): PrincipleCheckSummary =
        PrincipleCheckSummary(
            keptCount = keptCount,
            neutralCount = neutralCount,
            notKeptCount = notKeptCount
        )
}
