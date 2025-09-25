package com.depromeet.team5.core.domain.repository

import com.depromeet.team5.core.domain.model.Retrospection
import kotlinx.coroutines.flow.Flow


interface HedgeRepository {

    fun createRetrospection(body: Map<String, Any?>): Flow<Retrospection>
}
