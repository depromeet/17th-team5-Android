package com.depromeet.team5.core.data.datasource


interface LocalDataSource {
    suspend fun isConsumed(id: Int): Boolean
    suspend fun consume(id: Int): Boolean
}