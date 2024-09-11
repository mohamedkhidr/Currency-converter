package com.khidrew.data.dataSource.local.database.databaseEntities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
data class ConversionWithCurrencies(
    @Embedded val conversion: Conversion,

    @Relation(
        parentColumn = "from",
        entityColumn = "symbol"
    )
    val fromCurrency: Currency,

    @Relation(
        parentColumn = "to",
        entityColumn = "symbol"
    )
    val toCurrency: Currency
)