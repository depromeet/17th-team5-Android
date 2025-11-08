package com.depromeet.team5.core.domain.model

data class SystemPrinciple(
    val code: String,
    val message: String,
    val data: SystemPrincipleData,
)

data class SystemPrincipleData(
    val recommended: List<RecommendedPrinciple>,
    val defaults: List<DefaultPrinciple>,
)

data class RecommendedPrinciple(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val principleCount: Int,
    val investorName: String,
)

data class DefaultPrinciple(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val orderType: OrderType
)