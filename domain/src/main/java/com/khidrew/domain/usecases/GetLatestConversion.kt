package com.khidrew.domain.usecases

import com.khidrew.domain.repos.ConversionRepo
import com.khidrew.domain.repos.LatestConversionRepo

class GetLatestConversion(private val latestConversionRepo: LatestConversionRepo) {
        suspend operator fun invoke() = latestConversionRepo.getLatestConversion()

}