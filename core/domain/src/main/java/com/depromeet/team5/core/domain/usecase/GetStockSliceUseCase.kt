package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import javax.inject.Inject


class GetStockSliceUseCase @Inject constructor(
    private val repository: HedgeRepository
) {

    operator fun invoke(
        companyName: String,
        nextCursor: String?,
        size: Int?
    ) = repository.getStockSlice(
        companyName = companyName,
        nextCursor = nextCursor,
        size = size
    )
}
