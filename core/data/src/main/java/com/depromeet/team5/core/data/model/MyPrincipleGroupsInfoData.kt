package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType


data class MyPrincipleGroupsInfoData(
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
    val imageId: Int?,
    val principleType: String,
    val displayOrder: Int,
    val principles: List<MyPrincipleData>
) : DataMapper<MyPrincipleGroup> {

    override fun toDomain(): MyPrincipleGroup = MyPrincipleGroup(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        imageId = imageId,
        orderType = when (principleType) {
            "BUY" -> OrderType.BUY
            "SELL" -> OrderType.SELL
            else -> OrderType.NONE
        },
        displayOrder = displayOrder,
        principles = principles.map { it.toDomain() }
    )

}

data class MyPrincipleData(
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
        description = description,
    )
}
