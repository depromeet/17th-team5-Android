package com.depromeet.team5.core.remotedatasource.request

data class CreateRetrospectionRequestRemoteData(
    val symbol: String,
    val market: String,
    val currency: String,
    val orderDate: String,
    val orderType: String,
    val price: Int,
    val volume: Int,
    val returnRate: Double?,
    val principleChecks: List<PrincipleCheckRequestRemoteData>,
)

data class PrincipleCheckRequestRemoteData(
    val principleId: Int,
    val status: String,
    val reason: String,
    val imageIds: List<Int>,
    val links: List<String>,
)
