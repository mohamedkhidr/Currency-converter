package com.khidrew.data.mappers

import com.khidrew.data.dataSource.local.database.databaseEntities.Currency
import com.khidrew.domain.entities.CurrencyModel

object CurrencyMapper {
    fun mapCurrencyEntityToCurrencyModel(currency: Currency): CurrencyModel =
        CurrencyModel(
            currency.id,
            currency.symbol,
            currency.price
        )

    fun mapCurrencyModelToCurrencyEntity(currencyModel: CurrencyModel): Currency =
        Currency(
            currencyModel.id,
            currencyModel.symbol,
            currencyModel.price
        )
}