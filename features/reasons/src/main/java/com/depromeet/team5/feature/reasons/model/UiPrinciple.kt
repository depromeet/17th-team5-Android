package com.depromeet.team5.feature.reasons.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.depromeet.team5.core.domain.model.MyPrinciple
import com.depromeet.team5.core.domain.model.MyPrincipleGroup
import com.depromeet.team5.core.domain.model.OrderType
import com.depromeet.team5.core.domain.model.PrincipleChecks

fun MyPrincipleGroup.toUi() = UiPrincipleGroup(
    id = id,
    groupName = groupName,
    thumbnail = thumbnail,
    orderType = orderType,
    displayOrder = displayOrder,
    principles = principles.mapIndexed { idx, item -> item.toUi(idx) },
)

fun MyPrinciple.toUi(idx: Int) = UiPrinciple(
    id = id,
    groupId = groupId,
    principle = principle,
    description = description,
    principleChecks = principleChecks.toUi(idx),
)

fun PrincipleChecks.toUi(idx: Int) = UiPrincipleChecks(
    principleId = principleId,
    adherence = PrincipleAdherence.fromStatus(status).run {
        if (idx == 0 && this == PrincipleAdherence.UNSELECTED) PrincipleAdherence.KEPT else this
    },
    note = TextFieldValue(reason),
    imageUrls = imageUrls,
    articles = links.map {
        Article(
            originUrl = it,
            title = null,
            thumbnail = null,
            source = null,
        )
    },
)

@Immutable
data class UiPrincipleGroup(
    val id: Int,
    val groupName: String,
    val thumbnail: String,
    val orderType: OrderType,
    val displayOrder: Int,
    val principles: List<UiPrinciple>
) {

    fun getIndexOfFirstUnselectedPrinciple(): Int {
        return principles.indexOfFirst { it.principleChecks.adherence == PrincipleAdherence.UNSELECTED }
    }

    fun getCheckedPrincipleCount(): Int {
        return principles.count { it.principleChecks.adherence != PrincipleAdherence.UNSELECTED }
    }

    fun isAllPrincipleChecked(): Boolean {
        return getCheckedPrincipleCount() == principles.size
    }

    fun toDomain() = MyPrincipleGroup(
        id = id,
        groupName = groupName,
        thumbnail = thumbnail,
        orderType = orderType,
        displayOrder = displayOrder,
        principles = principles.map { it.toDomain() }
    )
}

@Immutable
data class UiPrinciple(
    val id: Int,
    val groupId: Int,
    val principle: String,
    val description: String,
    val principleChecks: UiPrincipleChecks,
) {
    fun toDomain() = MyPrinciple(
        id = id,
        groupId = groupId,
        principle = principle,
        description = description,
        principleChecks = principleChecks.toDomain()
    )
}

@Immutable
data class UiPrincipleChecks(
    val principleId: Int,
    val adherence: PrincipleAdherence,
    val note: TextFieldValue,
    val imageUrls: List<String>,
    val articles: List<Article>,
) {
    fun toDomain() = PrincipleChecks(
        principleId = principleId,
        status = adherence.name,
        reason = note.text,
        imageUrls = imageUrls,
        links = articles.map { it.originUrl }
    )
}

@Immutable
enum class PrincipleAdherence {
    UNSELECTED, KEPT, NEUTRAL, NOT_KEPT;

    companion object {
        fun fromStatus(status: String): PrincipleAdherence = when (status) {
            "KEPT" -> KEPT
            "NEUTRAL" -> NEUTRAL
            "NOT_KEPT" -> NOT_KEPT
            else -> UNSELECTED
        }
    }
}
