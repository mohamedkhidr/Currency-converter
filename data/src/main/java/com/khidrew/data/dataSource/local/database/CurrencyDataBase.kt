package com.khidrew.data.dataSource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khidrew.data.dataSource.local.database.dao.ConversionWithCurrenciesDao
import com.khidrew.data.dataSource.local.database.dao.ConversionsDao
import com.khidrew.data.dataSource.local.database.dao.CurrenciesDao
import com.khidrew.data.dataSource.local.database.databaseEntities.Conversion
import com.khidrew.data.dataSource.local.database.databaseEntities.ConversionWithCurrencies
import com.khidrew.data.dataSource.local.database.databaseEntities.Currency

@Database(
    entities = [Currency::class, Conversion::class],
    version = 4
)
abstract class CurrencyDataBase : RoomDatabase() {

    abstract fun symbolsDao(): CurrenciesDao
    abstract fun conversionDao(): ConversionsDao

    abstract fun conversionWithCurrenciesDao(): ConversionWithCurrenciesDao

}