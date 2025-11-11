package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.MemoRemoteData
import com.depromeet.team5.core.remotedatasource.model.PrincipleCheckGroupsRemoteData
import com.depromeet.team5.core.remotedatasource.model.PrincipleCheckRemoteData
import com.depromeet.team5.core.remotedatasource.model.RetrospectionRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.Serializable

@Serializable
data class RetrospectionResponse(
    val id: Int,
    val userId: Int,
    val market: String,
    val price: Double,
    val createdAt: String,
    val currency: String,
    val orderDate: String,
    val orderType: String,
    val returnRate: Double? = null,
    val symbol: String,
    val updatedAt: String,
    val volume: Int,
    val principleCheckGroup: PrincipleCheckGroupsResponse? = null,
    val memos: List<MemoResponse> = emptyList(),
) : RetrofitMapper<RetrospectionRemoteData> {

    override fun toRemoteData() = RetrospectionRemoteData(
        createdAt = createdAt,
        currency = currency,
        id = id,
        market = market,
        orderDate = orderDate,
        orderType = orderType,
        price = price.toInt(),
        returnRate = returnRate ?: 0.0,
        symbol = symbol,
        updatedAt = updatedAt,
        userId = userId,
        volume = volume,
        principleCheckGroupsRemoteData = principleCheckGroup?.toRemoteData(),
        memos = memos.map { it.toRemoteData() },
    )
}

@Serializable
data class PrincipleCheckGroupsResponse(
    val groupId: Int,
    val groupName: String,
    val thumbnail: String,
    val principleType: String,
    val principleCheckResponses: List<PrincipleCheckResponse>
) : RetrofitMapper<PrincipleCheckGroupsRemoteData> {
    override fun toRemoteData() = PrincipleCheckGroupsRemoteData(
        groupId = groupId,
        groupName = groupName,
        thumbnail = thumbnail,
        principleType = principleType,
        principleChecks = principleCheckResponses.map { it.toRemoteData() },
    )
}

@Serializable
data class PrincipleCheckResponse(
    val principleId: Int,
    val principle: String,
    val status: String,
    val reason: String,
    val imageUrls: List<String>,
    val links: List<String>,
) : RetrofitMapper<PrincipleCheckRemoteData> {
    override fun toRemoteData() = PrincipleCheckRemoteData(
        principleId = principleId,
        principle = principle,
        status = status,
        reason = reason,
        imageUrls = imageUrls,
        links = links,
    )
}

@Serializable
data class MemoResponse(
    val memoId: Int,
    val content: String,
    val createdAt: String,
) : RetrofitMapper<MemoRemoteData> {
    override fun toRemoteData() = MemoRemoteData(
        memoId = memoId,
        content = content,
    )
}