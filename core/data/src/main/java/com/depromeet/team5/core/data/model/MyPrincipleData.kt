package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup


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
    val displayOrder: Int,
    val principles: List<MyPrincipleInfoData>
) : DataMapper<MyPrincipleGroup> {

    override fun toDomain(): MyPrincipleGroup = MyPrincipleGroup(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
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
        groupName = groupName,
        principle = principle,
        description = description
    )
}
