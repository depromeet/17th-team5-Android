package com.depromeet.team5.core.ui.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

object HedgeDateFormatters {
    val input: List<DateTimeFormatter> = listOf(
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ISO_LOCAL_DATE,
        DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA),
    )

    val ymd: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA)
    val md: DateTimeFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREA)
    val yyM: DateTimeFormatter = DateTimeFormatter.ofPattern("yy년 M월", Locale.KOREA)
}

fun String.flexLocalDateOrNull(
    inputFormatters: List<DateTimeFormatter> = HedgeDateFormatters.input
): LocalDate? {
    for (fmt in inputFormatters) {
        val parsed = runCatching {
            if (fmt == DateTimeFormatter.ISO_LOCAL_DATE_TIME) {
                LocalDateTime.parse(this, fmt).toLocalDate()
            } else {
                LocalDate.parse(this, fmt)
            }
        }.getOrNull()
        if (parsed != null) return parsed
    }
    return null
}

fun String.toMonthDayOrRaw(
    outputFormatter: DateTimeFormatter = HedgeDateFormatters.md,
    inputFormatters: List<DateTimeFormatter> = HedgeDateFormatters.input
): String =
    this.flexLocalDateOrNull(inputFormatters)?.format(outputFormatter) ?: this

fun String.toYMDOrRaw(
    outputFormatter: DateTimeFormatter = HedgeDateFormatters.ymd,
    inputFormatters: List<DateTimeFormatter> = HedgeDateFormatters.input
): String =
    this.flexLocalDateOrNull(inputFormatters)?.format(outputFormatter) ?: this

fun LocalDate?.toSectionLabel(
    now: LocalDate = LocalDate.now(),
    outputFormatter: DateTimeFormatter = HedgeDateFormatters.yyM
): String {
    val thisMonth = YearMonth.from(now)
    val target = this?.let { YearMonth.from(it) } ?: return "기타"
    return when (target) {
        thisMonth -> "이번달 회고"
        thisMonth.minusMonths(1) -> "지난달 회고"
        else -> target.format(outputFormatter) + " 회고"
    }
}
