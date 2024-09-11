package com.khidrew.data.dataSource.remote.dtos

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: Map<String, Double>,
    @SerializedName("error")
    val error: ErrorResponse?,
)