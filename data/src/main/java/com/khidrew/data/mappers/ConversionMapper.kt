package com.khidrew.data.mappers

import com.khidrew.data.dataSource.local.database.databaseEntities.Conversion
import com.khidrew.data.dataSource.local.database.databaseEntities.ConversionWithCurrencies
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.entities.CurrencyModel

object ConversionMapper {
    fun mapConversionWithCurrenciesEntityToConversionModel(conversionEntity: ConversionWithCurrencies): ConversionModel =
        ConversionModel(
            conversionEntity.conversion.id,
            CurrencyMapper.mapCurrencyEntityToCurrencyModel(conversionEntity.fromCurrency),
            CurrencyMapper.mapCurrencyEntityToCurrencyModel(conversionEntity.toCurrency),
            conversionEntity.conversion.fromAmount,
            conversionEntity.conversion.toAmount,
            conversionEntity.conversion.timeStamp
        )


    fun mapConversionEntityToConversionModel(conversionEntity: Conversion): ConversionModel =
        ConversionModel(
            conversionEntity.id,
            CurrencyModel(symbol =  conversionEntity.from),
            CurrencyModel(symbol = conversionEntity.to),
            conversionEntity.fromAmount,
            conversionEntity.toAmount,
            conversionEntity.timeStamp
        )


    fun mapConversionModelToConversionEntity(conversionModel: ConversionModel): Conversion =
        Conversion(
            conversionModel.id,
            conversionModel.from.symbol,
            conversionModel.to.symbol,
            conversionModel.fromAmount,
            conversionModel.toAmount,
            conversionModel.timeStamp
        )


}