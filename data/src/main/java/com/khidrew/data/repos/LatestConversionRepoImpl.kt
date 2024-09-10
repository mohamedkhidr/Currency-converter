package com.khidrew.data.repos

import com.khidrew.data.dataSource.local.database.CurrencyDataBase
import com.khidrew.data.dataSource.local.database.databaseEntities.Conversion
import com.khidrew.data.mappers.ConversionMapper
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.repos.LatestConversionRepo
import javax.inject.Inject

class LatestConversionRepoImpl @Inject constructor(private val dataBase: CurrencyDataBase) :
    LatestConversionRepo {
    override suspend fun getLatestConversion(): ConversionModel {
        val item = dataBase.conversionDao().getLatestConversion()

        if (item == null) {
            dataBase.conversionDao().insertOrUpdate(
                Conversion(
                    from = "USD",
                    to = "EGP",
                    fromAmount = 1.0,
                    toAmount = 48.9,
                    timeStamp = System.currentTimeMillis()
                )
            )
        }
        return ConversionMapper.mapConversionEntityToConversionModel(
            dataBase.conversionDao().getLatestConversion()!!
        )
    }
}