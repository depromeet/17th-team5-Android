package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.DefaultPrinciple
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.RecommendedPrinciple
import com.depromeet.team5.core.domain.model.SystemPrinciple
import com.depromeet.team5.core.domain.model.SystemPrincipleData

data class SystemPrincipleData(
    val code: String,
    val message: String,
    val data: SystemPrincipleDataData,
) : DataMapper<SystemPrinciple> {
    override fun toDomain(): SystemPrinciple = SystemPrinciple(
        code = code,
        message = message,
        data = data.toDomain()
    )
}

data class SystemPrincipleDataData(
    val recommended: List<RecommendedPrincipleData>,
    val defaults: List<DefaultPrincipleData>,
) : DataMapper<SystemPrincipleData> {
    override fun toDomain(): SystemPrincipleData = SystemPrincipleData(
        recommended = recommended.map { it.toDomain() },
        defaults = defaults.map { it.toDomain() }
    )
}

data class RecommendedPrincipleData(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val principleCount: Int,
//    val userName: String,
) : DataMapper<RecommendedPrinciple> {
    override fun toDomain(): RecommendedPrinciple = RecommendedPrinciple(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        principleCount = principleCount,
//        userName = userName
    )
}

data class DefaultPrincipleData(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val principleType: String
) : DataMapper<DefaultPrinciple> {
    override fun toDomain(): DefaultPrinciple = DefaultPrinciple(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        orderType = if (principleType == "BUY") OrderType.BUY else OrderType.SELL
    )
}