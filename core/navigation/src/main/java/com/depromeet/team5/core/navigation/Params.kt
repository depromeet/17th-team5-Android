package com.depromeet.team5.core.navigation


enum class Path {
    PRINCIPLE_SYSTEM,
    PRINCIPLE_MINE,
    PRINCIPLE_RECOMMENDED
}

enum class PrincipleModificationType {
    ADD, MODIFY
}

const val IS_PRINCIPLE_UPDATED = "isPrincipleUpdated"
const val IS_RETROSPECTION_UPDATED = "isRetrospectionUpdated"

const val HIGHLIGHT_RETROSPECTION_ID = "highlightRetrospectionId"