package com.khidrew.data.dataSource.local.database.databaseEntities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "conversions",
    foreignKeys = [
        ForeignKey(
            entity = Currency::class,
            parentColumns = ["symbol"],
            childColumns = ["from"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Currency::class,
            parentColumns = ["symbol"],
            childColumns = ["to"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["from", "to"], unique = true)])
data class Conversion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val from: String,
    val to: String,
    val fromAmount: Double,
    val toAmount: Double,
    val timeStamp: Long
)
