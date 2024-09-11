package com.khidrew.data.dataSource.local.database.databaseEntities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "currencies", indices = [Index(value = ["symbol"], unique = true)])
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val symbol: String,
    val price: Double
)
