package com.khidrew.domain.repos

import com.khidrew.domain.apiStates.ApiStates

interface SyncLatestRatesRepo {
    suspend fun syncLatestRates() : ApiStates
}