package com.depromeet.team5.core.domain.model

data class RetrospectionList(
    val code: String,
    val message: String,
    val data: List<RetrospectionListSymbol>
)

data class RetrospectionListSymbol(
    val companyName: String,
    val retrospections: List<RetrospectionListItem>
)

data class RetrospectionListItem(
    val id: Int,
    val orderType: String,
    val price: Int,
    val volume: Int,
    val retrospectionCreatedAt: String,
    val orderCreatedAt: String
)