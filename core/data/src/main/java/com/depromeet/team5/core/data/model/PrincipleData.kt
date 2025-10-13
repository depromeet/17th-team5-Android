package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.Principle


data class PrincipleData(
    val title: String,
    val content: String
) : DataMapper<Principle> {

    override fun toDomain(): Principle = Principle(
        title = title,
        content = content
    )
}
