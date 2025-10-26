package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.MyPrinciple


data class MyPrincipleData(
    val code: String,
    val message: String,
    val data: List<MyPrincipleInfoData>
) : DataMapper<Map<Int, List<MyPrinciple>>> {

    override fun toDomain(): Map<Int, List<MyPrinciple>> = data.groupBy { it.groupId }
        .mapValues { entries ->
            entries.value
                .sortedBy { it.displayOrder }
                .map { it.toDomain() }
        }
}

data class MyPrincipleInfoData(
    val id: Int,
    val groupId: Int,
    val principle: String,
    val displayOrder: Int
) : DataMapper<MyPrinciple> {

    override fun toDomain(): MyPrinciple = MyPrinciple(
        id = id,
        groupId = groupId,
        principle = principle
    )
}
