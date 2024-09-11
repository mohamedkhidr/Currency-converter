package com.khidrew.data.repos

import com.khidrew.data.dataSource.local.database.CurrencyDataBase
import com.khidrew.data.mappers.ConversionMapper
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.repos.LatestConversionRepo
import javax.inject.Inject

class LatestConversionRepoImpl @Inject constructor(private val dataBase: CurrencyDataBase) :
    LatestConversionRepo {
    override suspend fun getLatestConversion(): ConversionModel? {
        val conv = dataBase.conversionWithCurrenciesDao().getLatestConversionWithCurrencies()
        return conv?.let { ConversionMapper.mapConversionWithCurrenciesEntityToConversionModel(conv) }
    }
}