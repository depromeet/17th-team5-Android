package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.Memo
import com.depromeet.team5.core.domain.model.PrincipleChecks
import com.depromeet.team5.core.domain.model.PrincipleGroupState
import com.depromeet.team5.core.domain.model.PrincipleState
import com.depromeet.team5.core.domain.model.PrincipleType
import com.depromeet.team5.core.domain.model.Retrospection
import com.depromeet.team5.core.domain.model.toOrderType

data class RetrospectionData(
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
    val principleCheckGroupData: PrincipleCheckGroupsData?,
    val memos: List<MemoData>,
) : DataMapper<Retrospection> {

    override fun toDomain(): Retrospection = Retrospection(
        id = id,
        userId = userId,
        market = market,
        companyName = companyName,
        companyLogo = companyLogo,
        price = price,
        createdAt = createdAt,
        currency = currency,
        orderDate = orderDate,
        orderType = orderType.toOrderType(),
        returnRate = returnRate,
        badge = badge,
        symbol = symbol,
        updatedAt = updatedAt,
        volume = volume,
        principleGroupState = principleCheckGroupData?.toDomain(),
        memos = memos.map { it.toDomain() }
    )
}


data class PrincipleCheckGroupsData(
    val groupId: Int,
    val groupName: String,
    val thumbnail: String,
    val principleType: String,
    val principleChecks: List<PrincipleCheckData>
) : DataMapper<PrincipleGroupState> {
    override fun toDomain() = PrincipleGroupState(
        id = groupId,
        groupName = groupName,
        thumbnail = thumbnail,
        principleType = PrincipleType.BUY,
        principles = principleChecks.map {
            it.toDomain()
        }
    )

}

data class PrincipleCheckData(
    val principleId: Int,
    val principle: String,
    val status: String,
    val reason: String,
    val imageUrls: List<String>,
    val links: List<String>,
) : DataMapper<PrincipleState> {
    override fun toDomain() = PrincipleState(
        id = principleId,
        groupId = principleId,
        principle = principle,
        description = "",
        principleChecks = PrincipleChecks(
            principleId = principleId,
            status = status,
            reason = reason,
            imageUrls = imageUrls,
            links = links,
        )
    )

}

data class MemoData(
    val memoId: Int,
    val content: String,
    val createdAt: String,
) : DataMapper<Memo> {
    override fun toDomain() = Memo(
        memoId = memoId,
        content = content,
        createdAt = createdAt,
    )
}