package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.Feedback
import com.depromeet.team5.core.domain.model.FeedbackInfo

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
) : DataMapper<FeedbackInfo> {
    override fun toDomain(): FeedbackInfo =
        FeedbackInfo(
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
