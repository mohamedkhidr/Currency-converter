package com.khidrew.data.repos

import com.khidrew.data.dataSource.local.database.CurrencyDataBase
import com.khidrew.data.mappers.ConversionMapper
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.repos.ConversionBySymbolRepo
import javax.inject.Inject

class ConversionBySymbolsRepoImpl @Inject constructor(private val dataBase: CurrencyDataBase) :
    ConversionBySymbolRepo {
    override suspend fun getConversionBySymbol(
        fromSymbol: String,
        toSymbol: String
    ): ConversionModel? {
        val conv = dataBase.conversionWithCurrenciesDao()
            .getLatestConversionWithCurrenciesBySymbols(fromSymbol, toSymbol)
        return conv?.let { ConversionMapper.mapConversionWithCurrenciesEntityToConversionModel(conv) }
    }
}