package com.depromeet.team5.core.ui.util

object CurrencyUtil {
    private val currencySymbols = mapOf(
        "KRW" to "원",
        "USD" to "$",
        "JPY" to "¥",
        "EUR" to "€"
    )

    fun getSymbol(code: String): String =
        currencySymbols[code] ?: code
}