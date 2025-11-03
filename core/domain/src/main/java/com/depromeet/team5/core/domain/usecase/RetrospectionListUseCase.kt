package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject

class RetrospectionListUseCase @Inject constructor(
    private val repository: HedgeRepository
){

    operator fun invoke() = repository.retrospectionList()
}