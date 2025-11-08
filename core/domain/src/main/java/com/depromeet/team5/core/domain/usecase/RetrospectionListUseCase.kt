package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.RetrospectionList
import com.depromeet.team5.core.domain.repository.HedgeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrospectionListUseCase @Inject constructor(
    private val repository: HedgeRepository
){

    operator fun invoke(): Flow<RetrospectionList> = repository.retrospectionList()
}