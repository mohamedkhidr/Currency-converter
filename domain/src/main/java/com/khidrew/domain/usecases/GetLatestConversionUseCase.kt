package com.khidrew.domain.usecases

import com.khidrew.domain.repos.LatestConversionRepo

class GetLatestConversionUseCase(private val latestConversionRepo: LatestConversionRepo) {
        suspend operator fun invoke() = latestConversionRepo.getLatestConversion()

}