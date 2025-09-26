package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.AnalysisRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.Serializable

@Serializable
data class AnalysisResponse(
    val code: String,
    val data: AnalysisData,
    val message: String
) : RetrofitMapper<AnalysisRemoteData> {
    override fun toRemoteData() = AnalysisRemoteData(
        code = code,
        text = data.text,
        message = message
    )
}

@Serializable
data class AnalysisData(
    val text: String
)
