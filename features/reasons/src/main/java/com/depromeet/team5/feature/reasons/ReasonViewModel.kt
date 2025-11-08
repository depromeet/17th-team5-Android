package com.depromeet.team5.feature.reasons

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depromeet.team5.core.domain.monad.HedgeUiState
import com.depromeet.team5.core.ui.lazy.HedgeState
import com.depromeet.team5.core.ui.lazy.hedgeState
import com.depromeet.team5.feature.reasons.model.Article
import com.depromeet.team5.feature.reasons.model.PrincipleAdherence
import com.depromeet.team5.feature.reasons.model.TradeInfo
import com.depromeet.team5.feature.reasons.model.UiPrincipleChecks
import com.depromeet.team5.feature.reasons.model.UiPrincipleGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class ReasonViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var initialPrincipleGroup: UiPrincipleGroup? = null
    private val tradeInfo: HedgeState<TradeInfo?> by hedgeState(null)
    private val principleGroup by hedgeState(initialPrincipleGroup)

    val hedgeUiState = combine(tradeInfo.stateFlow, principleGroup.stateFlow) { tradeInfo, principleTemplate ->
        if (tradeInfo == null || principleTemplate == null) HedgeUiState.Loading(null)
        else HedgeUiState.Success(tradeInfo to principleTemplate)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HedgeUiState.Loading(null)
    )

    fun initTradeInfo(tradeInfo: TradeInfo) = this.tradeInfo.update { tradeInfo }

    fun initPrincipleGroup(principleTemplate: UiPrincipleGroup) {
        initialPrincipleGroup = principleTemplate
        this.principleGroup.update { principleTemplate }
    }

    fun onAdherenceChanged(id: Int, value: PrincipleAdherence) = updatePrincipleGroup(id) { it.copy(adherence = value) }

    fun onNoteChanged(id: Int, value: TextFieldValue) = updatePrincipleGroup(id) { it.copy(note = value) }

    fun onAddImages(id: Int, url: List<String>) = updatePrincipleGroup(id) { it.copy(imageUrls = it.imageUrls + url) }

    fun onRemoveImage(id: Int, index: Int) = updatePrincipleGroup(id) {
        it.copy(imageUrls = it.imageUrls.toMutableList().apply { if (index in indices) removeAt(index) })
    }

    fun onAddArticle(id: Int, link: String) {
        viewModelScope.launch {
            val article = parseArticle(link)
            updatePrincipleGroup(id) {
                it.copy(
                    articles = it.articles + article
                )
            }
        }
    }

    fun onRemoveArticle(id: Int, index: Int) = updatePrincipleGroup(id) {
        it.copy(articles = it.articles.toMutableList().apply { if (index in indices) removeAt(index) })
    }

    private suspend fun parseArticle(url: String): Article = withContext(Dispatchers.IO) {
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

    private inline fun updatePrincipleGroup(
        idx: Int,
        crossinline transform: (UiPrincipleChecks) -> UiPrincipleChecks
    ) {
        principleGroup.update {
            it?.let { template ->
                val old = template.principles[idx].principleChecks
                val newItem = transform(old)
                val newList =
                    template.principles.toMutableList().apply { set(idx, template.principles[idx].copy(principleChecks = newItem)) }
                template.copy(principles = newList)
            }
        }
    }

    companion object {
        const val PRINCIPLE_ATTACHMENT_LIMIT = 3
    }
}