package com.khidrew.domain.repos

import com.khidrew.domain.entities.ConversionModel

interface ConversionInsertionRepo {
    suspend fun insertOrUpdateConversion(convertionModel: ConversionModel)
}