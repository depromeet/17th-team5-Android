package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DomainMapper
import com.depromeet.team5.core.domain.model.PrincipleEntity


data class PrincipleData(
    val title: String,
    val content: String
) : DomainMapper<PrincipleEntity> {

    override fun toDomain(): PrincipleEntity = PrincipleEntity(
        title = title,
        content = content
    )
}
