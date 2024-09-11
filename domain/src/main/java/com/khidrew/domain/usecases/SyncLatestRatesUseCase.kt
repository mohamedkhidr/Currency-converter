package com.khidrew.domain.usecases

import com.khidrew.domain.repos.SyncLatestRatesRepo

class SyncLatestRatesUseCase(private val syncLatestRatesRepo: SyncLatestRatesRepo) {
    suspend operator fun invoke() = syncLatestRatesRepo.syncLatestRates()
}