package com.depromeet.team5.core.remotedatasource.datasourceimpl

import androidx.core.net.toUri
import com.depromeet.team5.core.data.datasource.RetrospectionRemoteDataSource
import com.depromeet.team5.core.data.model.ArticleData
import com.depromeet.team5.core.data.model.BaseData
import com.depromeet.team5.core.data.model.MemoData
import com.depromeet.team5.core.data.model.RetrospectionData
import com.depromeet.team5.core.data.request.CreateRetrospectionRequestData
import com.depromeet.team5.core.remotedatasource.apisource.RetrospectionApiSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URI
import com.depromeet.team5.core.remotedatasource.mapper.toRemoteData
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
internal class RetrospectionRemoteDataSourceImpl @Inject constructor(
    private val retrospectionApiSource: RetrospectionApiSource,
) : RetrospectionRemoteDataSource {

    override suspend fun createRetrospection(request: CreateRetrospectionRequestData): BaseData<RetrospectionData> =
        retrospectionApiSource.createRetrospection(request.toRemoteData()).toBaseData()

    override suspend fun getRetrospection(retrospectionId: Int): BaseData<RetrospectionData> =
        retrospectionApiSource.getRetrospection(retrospectionId).toBaseData()

    override suspend fun deleteRetrospection(retrospectionId: Int): BaseData<String> =
        retrospectionApiSource.deleteRetrospection(retrospectionId).toBaseData()

    override suspend fun uploadImageUri(
        domain: String,
        uri: String,
        fileName: String?
    ) = retrospectionApiSource.uploadImageUri(
        domain = domain,
        uri = uri.toUri(),
        fileName = fileName
    )

    override suspend fun createMemo(retrospectionId: Int, content: String): BaseData<MemoData> =
        retrospectionApiSource.createMemo(retrospectionId, content).toBaseData()

    override suspend fun updateMemo(retrospectionId: Int, memoId: Int, content: String): BaseData<MemoData> =
        retrospectionApiSource.updateMemo(retrospectionId, memoId, content).toBaseData()

    override suspend fun deleteMemo(retrospectionId: Int, memoId: Int): BaseData<String> =
        retrospectionApiSource.deleteMemo(retrospectionId, memoId).toBaseData()

    override suspend fun parseArticle(url: String): ArticleData = withContext(Dispatchers.IO) {
        val doc = kotlin.runCatching { Jsoup.connect(url).get() }.getOrNull() ?: return@withContext ArticleData(url, null, null, null)

        val title = doc.selectFirst("meta[property=og:title]")?.attr("content")
            ?: doc.title()

        val ogImage = doc.selectFirst("meta[property=og:image]")?.attr("content")
        val twitterImage = doc.selectFirst("meta[name=twitter:image]")?.attr("content")
        val thumbnail = ogImage ?: twitterImage

        val source = doc.selectFirst("meta[property=og:site_name]")?.attr("content")
            ?: doc.selectFirst("meta[name=author]")?.attr("content")
            ?: URI(url).host

        ArticleData(
            originUrl = url,
            title = title,
            thumbnail = thumbnail,
            source = source
        )
    }
}
