package com.khidrew.data.repos

import com.khidrew.data.dataSource.local.database.CurrencyDataBase
import com.khidrew.data.mappers.CurrencyMapper
import com.khidrew.domain.entities.CurrencyModel
import com.khidrew.domain.repos.CurrenciesRepo

class CurrenciesRepoImpl(private val dataBase: CurrencyDataBase) : CurrenciesRepo {
    override suspend fun getCurrencies(): List<CurrencyModel> {
        return dataBase.symbolsDao().getAllSymbols()
            .map { CurrencyMapper.mapCurrencyEntityToCurrencyModel(it) }
    }
}