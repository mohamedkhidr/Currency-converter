package com.khidrew.domain.repos

import com.khidrew.domain.entities.ConversionModel

interface LatestConversionRepo {
    suspend fun getLatestConversion(): ConversionModel?
}