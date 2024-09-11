package com.khidrew.domain.usecases

import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.repos.ConversionInsertionRepo

class InsertOrUpdateConversionUseCase(private val conversionInsertionRepo: ConversionInsertionRepo) {
    suspend operator fun invoke(conversionModel: ConversionModel) =
        conversionInsertionRepo.insertOrUpdateConversion(conversionModel)
}