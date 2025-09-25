package com.depromeet.team5.core.domain.model


data class RetrospectionEntity(
    val id: Int,
    val userId: Int,
    val market: String,
    val price: Int,
    val content: String,
    val createdAt: String,
    val currency: String,
    val emotion: String,
    val orderDate: String,
    val orderType: String,
    val returnRate: Double,
    val symbol: String,
    val updatedAt: String,
    val volume: Int
)
