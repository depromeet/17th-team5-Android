package com.depromeet.team5.core.data.request

data class CreateRetrospectionRequestData(
    val symbol: String,
    val market: String,
    val currency: String,
    val orderDate: String,
    val orderType: String,
    val price: Int,
    val volume: Int,
    val returnRate: Double?,
    val principleChecks: List<PrincipleCheckRequestData>,
)

data class PrincipleCheckRequestData(
    val principleId: Int,
    val status: String,
    val reason: String,
    val imageUrls: List<Int>,
    val links: List<String>,
)
