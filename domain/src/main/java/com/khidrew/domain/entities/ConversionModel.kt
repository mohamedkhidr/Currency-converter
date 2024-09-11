package com.khidrew.domain.entities

data class ConversionModel(
    val id: Int = 0,
    val from: CurrencyModel,
    val to: CurrencyModel,
    val fromAmount: Double,
    val toAmount: Double,
    val timeStamp: Long
)
