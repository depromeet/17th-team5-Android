package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.BaseDomain
import com.depromeet.team5.core.domain.model.Memo
import com.depromeet.team5.core.domain.repository.RetrospectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UpdateMemoUseCase @Inject constructor(
    private val retrospectionRepository: RetrospectionRepository
) {
    operator fun invoke(
        retrospectionId: Int,
        memoId: Int,
        content: String,
    ): Flow<BaseDomain<Memo>> =
        retrospectionRepository.updateMemo(retrospectionId, memoId, content)
}