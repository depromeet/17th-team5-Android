package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.repository.RetrospectionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


class UpdateImageUrisUseCase @Inject constructor(
    private val retrospectionRepository: RetrospectionRepository
) {
    suspend operator fun invoke(
        domain: String,
        imageUrls: List<String>,
        fileName: String? = null
    ): List<Int> = coroutineScope {
        imageUrls
            .map {
                async {
                    retrospectionRepository.uploadImageUri(
                        domain = domain,
                        uri = it,
                        fileName = fileName
                    )
                }
            }
            .awaitAll()
    }
}