package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.MemoData
import com.depromeet.team5.core.data.model.PrincipleCheckData
import com.depromeet.team5.core.data.model.PrincipleCheckGroupsData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

data class RetrospectionRemoteData(
    val id: Int,
    val userId: Int,
    val market: String,
    val companyName: String,
    val companyLogo: String?,
    val price: Int,
    val createdAt: String,
    val currency: String,
    val orderDate: String,
    val orderType: String,
    val returnRate: Double?,
    val badge: String,
    val symbol: String,
    val updatedAt: String,
    val volume: Int,
    val principleCheckGroupRemoteData: PrincipleCheckGroupRemoteData?,
    val memos: List<MemoRemoteData>,
) : RemoteDataMapper<RetrospectionData> {

    override fun toData(): RetrospectionData = RetrospectionData(
        createdAt = createdAt,
        currency = currency,
        id = id,
        market = market,
        companyName = companyName,
        companyLogo = companyLogo,
        orderDate = orderDate,
        orderType = orderType,
        price = price,
        returnRate = returnRate,
        badge = badge,
        symbol = symbol,
        updatedAt = updatedAt,
        userId = userId,
        volume = volume,
        principleCheckGroupData = principleCheckGroupRemoteData?.toData(),
        memos = memos.map { it.toData() }
    )
}

data class PrincipleCheckGroupRemoteData(
    val groupId: Int,
    val groupName: String,
    val thumbnail: String,
    val principleType: String,
    val principleChecks: List<PrincipleCheckRemoteData>
) : RemoteDataMapper<PrincipleCheckGroupsData> {
    override fun toData() = PrincipleCheckGroupsData(
        groupId = groupId,
        groupName = groupName,
        thumbnail = thumbnail,
        principleType = principleType,
        principleChecks = principleChecks.map { it.toData() },
    )
}

data class PrincipleCheckRemoteData(
    val principleId: Int,
    val principle: String,
    val status: String,
    val reason: String,
    val imageUrls: List<String>,
    val links: List<String>,
) : RemoteDataMapper<PrincipleCheckData> {
    override fun toData() = PrincipleCheckData(
        principleId = principleId,
        principle = principle,
        status = status,
        reason = reason,
        imageUrls = imageUrls,
        links = links,
    )
}

data class MemoRemoteData(
    val memoId: Int,
    val content: String,
    val createdAt: String,
) : RemoteDataMapper<MemoData> {
    override fun toData() = MemoData(
        memoId = memoId,
        content = content,
        createdAt = createdAt,
    )
}

