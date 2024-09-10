package com.khidrew.currency.models

import com.khidrew.data.dataSource.local.database.databaseEntities.Conversion
import com.khidrew.domain.entities.ConversionModel

sealed class ConversionListItem {
    data class DateHeader(val date:String): ConversionListItem()
    data class ConversionItem(val conversion: ConversionModel): ConversionListItem()
}