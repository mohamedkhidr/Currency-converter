package com.khidrew.domain.entities

data class ConversionModel(
    val id: Int = 0,
    val from: String,
    val to: String,
    val fromAmount: Double,
    val toAmount: Double,
    val timeStamp: Long
)
