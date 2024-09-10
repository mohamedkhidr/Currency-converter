package com.khidrew.data.repos

import com.khidrew.data.dataSource.local.database.CurrencyDataBase
import com.khidrew.data.mappers.ConversionMapper
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.repos.ConversionInsertionRepo
import javax.inject.Inject

class ConversionInsertionRepoImpl @Inject constructor(private val dataBase: CurrencyDataBase) :
    ConversionInsertionRepo {
    override suspend fun insertOrUpdateConversion(convertionModel: ConversionModel) {
        dataBase.conversionDao()
            .insertOrUpdate(ConversionMapper.mapConversionModelToConversionEntity(convertionModel))
    }
}