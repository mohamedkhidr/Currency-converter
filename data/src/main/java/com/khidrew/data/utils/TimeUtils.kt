package com.khidrew.data.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object TimeUtils {
    fun getFourDaysAgoTimestamp(): Long {
        val fourDaysAgo = LocalDateTime.now().minusDays(4)
        return fourDaysAgo.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }


    fun formatDateToBeReadable(timeStamp:Long, formatter:SimpleDateFormat) : String{
        val givenDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault())

        val now = LocalDateTime.now()

        val startOfToday = now.toLocalDate().atStartOfDay()

        val startOfYesterday = startOfToday.minusDays(1)

        // End of today (start of tomorrow)
        val endOfToday = startOfToday.plusDays(1)

        return when {
            givenDateTime.isAfter(startOfYesterday) && givenDateTime.isBefore(endOfToday) -> {
                if (givenDateTime.toLocalDate() == now.toLocalDate()) {
                    "Today"
                } else {
                    "Yesterday"
                }
            }
            else -> formatter.format(timeStamp)
        }
    }
}