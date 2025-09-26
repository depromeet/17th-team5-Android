package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.AnalysisEntity

data class AnalysisData(
    val code: String,
    val text: String,
    val message: String
) : DataMapper<AnalysisEntity> {

    override fun toDomain(): AnalysisEntity = AnalysisEntity(
        code = code,
        text = text,
        message = message,
    )

}
