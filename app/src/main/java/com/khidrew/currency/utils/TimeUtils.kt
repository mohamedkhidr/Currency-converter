package com.khidrew.currency.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object TimeUtils {
    fun isWithinLastFourDays(timestamp: Long): Boolean {
        val givenDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
        val currentDateTime = LocalDateTime.now()
        val fourDaysAgo = currentDateTime.minusDays(4)
        return givenDateTime.isAfter(fourDaysAgo) && givenDateTime.isBefore(currentDateTime)
    }
}