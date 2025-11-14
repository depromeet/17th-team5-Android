package com.depromeet.team5.core.domain.repository

interface BadgeDotRepository {
    suspend fun isConsumed(retrospectionId: Int): Boolean

    suspend fun consume(retrospectionId: Int): Boolean
}