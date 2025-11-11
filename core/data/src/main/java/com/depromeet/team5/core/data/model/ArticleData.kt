package com.depromeet.team5.core.data.model

import com.depromeet.team5.core.data.mapper.DataMapper
import com.depromeet.team5.core.domain.model.Article

data class ArticleData(
    val originUrl: String,
    val title: String?,
    val thumbnail: String?,
    val source: String?
) : DataMapper<Article> {
    override fun toDomain() = Article(
        originUrl = originUrl,
        title = title,
        thumbnail = thumbnail,
        source = source
    )
}