package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class SearchUseCase @Inject constructor(
    private val repository: HedgeRepository
) {

    operator fun invoke(query: String) = repository.search(query)
}
