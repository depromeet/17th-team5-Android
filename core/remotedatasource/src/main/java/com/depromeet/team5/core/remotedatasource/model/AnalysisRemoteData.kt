package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.AnalysisData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

data class AnalysisRemoteData(
    val code: String,
    val text: String,
    val message: String
) : RemoteDataMapper<AnalysisData> {
    override fun toData(): AnalysisData = AnalysisData(
        code = code,
        text = text,
        message = message,
    )
}