package com.khidrew.domain.usecases

import com.khidrew.domain.repos.ConversionRepo

class GetConversionsUseCase (private val conversionRepo: ConversionRepo) {
    suspend operator fun invoke() = conversionRepo.getConversions()
}