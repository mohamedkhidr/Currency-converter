package com.khidrew.domain.usecases

import com.khidrew.domain.repos.CurrenciesRepo

class GetCurrenciesUseCase (private val currenciesRepo: CurrenciesRepo){
    suspend operator fun invoke() = currenciesRepo.getCurrencies()
}