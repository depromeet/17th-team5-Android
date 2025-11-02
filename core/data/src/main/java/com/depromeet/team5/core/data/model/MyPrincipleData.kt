package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.MyPrinciple


data class MyPrincipleData(
    val code: String,
    val message: String,
    val data: List<MyPrincipleInfoData>
) : DataMapper<Map<String, List<MyPrinciple>>> {

    override fun toDomain(): Map<String, List<MyPrinciple>> = data.groupBy { it.groupName }
        .mapValues { entries ->
            entries.value
                .sortedBy { it.displayOrder }
                .map { it.toDomain() }
        }
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
