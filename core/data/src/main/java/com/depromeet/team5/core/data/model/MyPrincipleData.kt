package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType


data class MyPrincipleData(
    val code: String,
    val message: String,
    val data: List<MyPrincipleGroupData>
) : DataMapper<List<MyPrincipleGroup>> {

    override fun toDomain(): List<MyPrincipleGroup> = data.map { it.toDomain() }
}

data class MyPrincipleGroupData(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val principleType: String,
    val displayOrder: Int,
    val principles: List<MyPrincipleInfoData>
) : DataMapper<MyPrincipleGroup> {

    override fun toDomain(): MyPrincipleGroup = MyPrincipleGroup(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        orderType = when (principleType) {
            "BUY" -> OrderType.BUY
            "SELL" -> OrderType.SELL
            else -> OrderType.NONE
        },
        displayOrder = displayOrder,
        principles = principles.map { it.toDomain() }
    )

}

data class MyPrincipleInfoData(
    val id: Int,
    val groupId: Int,
    val groupName: String,
    val principle: String,
    val description: String,
    val displayOrder: Int
) : DataMapper<MyPrinciple> {

    override fun toDomain(): MyPrinciple = MyPrinciple(
        id = id,
        groupId = groupId,
        principle = principle,
        description = description
    )
}
