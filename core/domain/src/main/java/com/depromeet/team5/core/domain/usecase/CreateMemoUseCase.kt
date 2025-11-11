package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.BaseDomain
import com.depromeet.team5.core.domain.model.Memo
import com.depromeet.team5.core.domain.repository.RetrospectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CreateMemoUseCase @Inject constructor(
    private val retrospectionRepository: RetrospectionRepository
) {
    operator fun invoke(retrospectionId: Int, content: String): Flow<BaseDomain<Memo>> =
        retrospectionRepository.createMemo(retrospectionId, content)
}