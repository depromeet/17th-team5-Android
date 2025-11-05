package com.depromeet.team5.core.remotedatasource.model

import com.depromeet.team5.core.data.model.DefaultPrincipleData
import com.depromeet.team5.core.data.model.RecommendedPrincipleData
import com.depromeet.team5.core.data.model.SystemPrincipleData
import com.depromeet.team5.core.data.model.SystemPrincipleDataData
import com.depromeet.team5.core.remotedatasource.mapper.RemoteDataMapper

data class SystemPrincipleRemoteData(
    val code: String,
    val message: String,
    val data: SystemPrincipleDataRemoteData,
) : RemoteDataMapper<SystemPrincipleData> {
    override fun toData(): SystemPrincipleData = SystemPrincipleData(
        code = code,
        message = message,
        data = data.toData()
    )
}

data class SystemPrincipleDataRemoteData(
    val recommended: List<RecommendedPrincipleRemoteData>,
    val defaults: List<DefaultPrincipleRemoteData>,
) : RemoteDataMapper<SystemPrincipleDataData> {
    override fun toData(): SystemPrincipleDataData =
        SystemPrincipleDataData(
            recommended = recommended.map { it.toData() },
            defaults = defaults.map { it.toData() }
        )
}

data class RecommendedPrincipleRemoteData(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val principleCount: Int,
//    val userName: String,
) : RemoteDataMapper<RecommendedPrincipleData> {
    override fun toData(): RecommendedPrincipleData = RecommendedPrincipleData(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        principleCount = principleCount,
//        userName = userName
    )
}

data class DefaultPrincipleRemoteData(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val principleType: String
) : RemoteDataMapper<DefaultPrincipleData> {
    override fun toData(): DefaultPrincipleData = DefaultPrincipleData(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        principleType = principleType
    )
}