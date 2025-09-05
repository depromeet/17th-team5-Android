package com.depromeet.team5.core.domain.repository


interface TestRepository {

    suspend fun print(): String
}
