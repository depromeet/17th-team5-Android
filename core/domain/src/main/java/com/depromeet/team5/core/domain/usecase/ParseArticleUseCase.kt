package com.depromeet.team5.core.domain.usecase

import com.depromeet.team5.core.domain.model.Article
import com.depromeet.team5.core.domain.repository.RetrospectionRepository
import javax.inject.Inject

class ParseArticleUseCase @Inject constructor(
    private val retrospectionRepository: RetrospectionRepository
) {
    suspend operator fun invoke(url: String): Article = retrospectionRepository.parseArticle(url)
}