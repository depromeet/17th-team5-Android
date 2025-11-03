package com.depromeet.team5.feature.reasons

import androidx.annotation.DrawableRes
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.navigation.request.OrderTypeParams
import com.depromeet.team5.core.ui.lazy.hedgeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URI
import javax.inject.Inject

data class TradeInfo(
    @DrawableRes
    val logoDrawableRes: Int,
    val stockName: String,
    val orderType: OrderTypeParams,
    val price: Long,
    val currency: String,
    val volume: Int,
    val orderDate: String,
)

/*
* 업데이트시켜야 할 params
* principle
*   adherence
*   note (비고)
*   List<uri>
*   List<article>
* */

data class Principle(
    val id: Int,
    val title: String,
    val description: String,
    val adherence: PrincipleAdherence,
    val note: TextFieldValue,
    val images: List<String>,
    val articles: List<Article>,
)

enum class PrincipleAdherence {
    UNSELECTED, KEEP, NEUTRAL, BREAK,
}

enum class RestrictionAttachment {
    IMAGE, LINK,
}

data class PrincipleTemplate(
    val id: Int,
    val name: String,
    val emoji: String,
    val principles: List<Principle>
) {

    fun getIndexOfFirstUnselectedPrinciple(): Int {
        return principles.indexOfFirst { it.adherence == PrincipleAdherence.UNSELECTED }
    }

    fun getCheckedPrincipleCount(): Int {
        return principles.count { it.adherence != PrincipleAdherence.UNSELECTED }
    }

    fun isAllPrincipleChecked(): Boolean {
        return getCheckedPrincipleCount() == principles.size
    }

    companion object {
        val RETROSPECT_ENTRY = PrincipleTemplate(
            id = 0,
            name = "이건 진짜 지켜야 해",
            emoji = "👍",
            principles = listOf(
                Principle(
                    id = 0,
                    title = "종목 선택 시 최근 매출액 확인하기",
                    description = "상승장에서 눌림목 나오면 지지선 나올 때까지 기다렸다가 분할 매수하자. 몰빵은 절대 금지",
                    adherence = PrincipleAdherence.KEEP,
                    note = TextFieldValue(""),
                    images = emptyList(),
                    articles = emptyList(),
                ),
                Principle(
                    id = 1,
                    title = "커뮤니티 반응 보고 투자 금지",
                    description = "description",
                    adherence = PrincipleAdherence.UNSELECTED,
                    note = TextFieldValue(""),
                    images = emptyList(),
                    articles = emptyList(),
                ),
                Principle(
                    id = 2,
                    title = "감정 로그 활용하기",
                    description = "감정 로그를 활용해 ‘불안 시점 vs 실제 하락률’을 비교하면 정확도가 높아진다고 한다. 감정 로그를 꼭 확인하자!",
                    adherence = PrincipleAdherence.UNSELECTED,
                    note = TextFieldValue(""),
                    images = emptyList(),
                    articles = emptyList(),
                ),
                Principle(
                    id = 3,
                    title = "본질 가치보다 낮게 거래되는 주식 찾아 장기 보유하기",
                    description = "기업의 본질 가치보다 낮게 거래되는 주식을 찾아 장기 보유하기",
                    adherence = PrincipleAdherence.UNSELECTED,
                    note = TextFieldValue(""),
                    images = emptyList(),
                    articles = emptyList(),
                )
            ),
        )
    }
}

data class Article(
    val originUrl: String,
    val title: String?,
    val thumbnail: String?,
    val source: String?
)

@HiltViewModel
class ReasonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val tradeInfo by hedgeState(dummyTradeInfo)
    val initialPrincipleTemplate = PrincipleTemplate.RETROSPECT_ENTRY
    val principleTemplate by hedgeState(initialPrincipleTemplate)

    fun initTradeInfo(tradeInfo: TradeInfo) = this.tradeInfo.update { tradeInfo }

    fun onAdherenceChanged(id: Int, value: PrincipleAdherence) = updatePrincipleTemplate(id) { it.copy(adherence = value) }

    fun onNoteChanged(id: Int, value: TextFieldValue) = updatePrincipleTemplate(id) { it.copy(note = value) }

    fun onAddImages(id: Int, url: List<String>) = updatePrincipleTemplate(id) { it.copy(images = it.images + url) }

    fun onRemoveImage(id: Int, index: Int) = updatePrincipleTemplate(id) {
        it.copy(images = it.images.toMutableList().apply { if (index in indices) removeAt(index) })
    }

    fun onAddArticle(id: Int, link: String) {
        viewModelScope.launch {
            val article = fetchHtmlAndParse(link)
            updatePrincipleTemplate(id) {
                it.copy(
                    articles = it.articles + article
                )
            }
        }
    }

    fun onRemoveArticle(id: Int, index: Int) = updatePrincipleTemplate(id) {
        it.copy(articles = it.articles.toMutableList().apply { if (index in indices) removeAt(index) })
    }

    private suspend fun fetchHtmlAndParse(url: String): Article = withContext(Dispatchers.IO) {
        val doc = kotlin.runCatching { Jsoup.connect(url).get() }.getOrNull() ?: return@withContext Article(url, null, null, null)

        val title = doc.selectFirst("meta[property=og:title]")?.attr("content")
            ?: doc.title()

        val ogImage = doc.selectFirst("meta[property=og:image]")?.attr("content")
        val twitterImage = doc.selectFirst("meta[name=twitter:image]")?.attr("content")
        val thumbnail = ogImage ?: twitterImage

        val source = doc.selectFirst("meta[property=og:site_name]")?.attr("content")
            ?: doc.selectFirst("meta[name=author]")?.attr("content")
            ?: URI(url).host

        Article(
            originUrl = url,
            title = title,
            thumbnail = thumbnail,
            source = source
        )
    }

    private inline fun updatePrincipleTemplate(
        id: Int,
        crossinline transform: (Principle) -> Principle
    ) {
        principleTemplate.update { template ->
            val idx = template.principles.indexOfFirst { it.id == id }
            if (idx < 0) template else {
                val old = template.principles[idx]
                val newItem = transform(old)
                val newList = template.principles.toMutableList().apply { set(idx, newItem) }
                template.copy(
                    principles = newList,
                )
            }
        }
    }

    companion object {

        const val PRINCIPLE_ATTACHMENT_LIMIT = 3

        val dummyTradeInfo = TradeInfo(
            logoDrawableRes = R.drawable.ic_company_logo,
            stockName = "Apple",
            orderType = OrderTypeParams.BUY,
            price = 65000,
            currency = "$",
            volume = 3,
            orderDate = "2023년 8월 25일",
        )
    }
}