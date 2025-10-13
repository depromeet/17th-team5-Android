package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.Analysis

data class AnalysisData(
    val code: String,
    val text: String,
    val message: String
) : DataMapper<Analysis> {

    override fun toDomain(): Analysis = Analysis(
        code = code,
        text = text,
        message = message,
    )

}
