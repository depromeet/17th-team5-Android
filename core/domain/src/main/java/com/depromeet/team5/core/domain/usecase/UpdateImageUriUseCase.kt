package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.HedgeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


class UpdateImageUriUseCase @Inject constructor(
    private val hedgeRepository: HedgeRepository
) {

    suspend operator fun invoke(
        domain: String,
        imageUrls: List<String>,
        fileName: String? = null
    ): List<Int> = coroutineScope {
        imageUrls.map {
            async {
                hedgeRepository.uploadImageUri(
                    domain = domain,
                    uri = it,
                    fileName = fileName
                )
            }
        }
            .awaitAll()
    }
}