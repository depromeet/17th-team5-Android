package com.depromeet.team5.core.retrofit.model

import com.depromeet.team5.core.remotedatasource.model.DefaultPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.RecommendedPrincipleRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleDataRemoteData
import com.depromeet.team5.core.remotedatasource.model.SystemPrincipleRemoteData
import com.depromeet.team5.core.retrofit.mapper.RetrofitMapper
import kotlinx.serialization.Serializable

@Serializable
data class SystemPrincipleResponse(
    val code: String,
    val message: String,
    val data: SystemPrincipleDataResponse,
) : RetrofitMapper<SystemPrincipleRemoteData> {
    override fun toRemoteData(): SystemPrincipleRemoteData = SystemPrincipleRemoteData(
        code = code,
        message = message,
        data = data.toRemoteData()
    )
}

@Serializable
data class SystemPrincipleDataResponse(
    val recommended: List<RecommendedPrincipleResponse>,
    val defaults: List<DefaultPrincipleResponse>,
) : RetrofitMapper<SystemPrincipleDataRemoteData> {
    override fun toRemoteData(): SystemPrincipleDataRemoteData =
        SystemPrincipleDataRemoteData(
            recommended = recommended.map { it.toRemoteData() },
            defaults = defaults.map { it.toRemoteData() }
        )
}

@Serializable
data class RecommendedPrincipleResponse(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val principleCount: Int,
//    val userName: String,
) : RetrofitMapper<RecommendedPrincipleRemoteData> {
    override fun toRemoteData(): RecommendedPrincipleRemoteData = RecommendedPrincipleRemoteData(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        principleCount = principleCount,
//        userName = userName
    )
}

@Serializable
data class DefaultPrincipleResponse(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val principleType: String
) : RetrofitMapper<DefaultPrincipleRemoteData> {
    override fun toRemoteData(): DefaultPrincipleRemoteData = DefaultPrincipleRemoteData(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        principleType = principleType
    )
}