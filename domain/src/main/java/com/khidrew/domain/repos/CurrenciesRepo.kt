package com.khidrew.domain.repos

import com.khidrew.domain.entities.CurrencyModel

interface CurrenciesRepo {
    suspend fun getCurrencies():List<CurrencyModel>
}