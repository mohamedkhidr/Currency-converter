package com.khidrew.data.dataSource.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.khidrew.data.dataSource.local.database.databaseEntities.ConversionWithCurrencies
@Dao
interface ConversionWithCurrenciesDao {
    @Transaction
    @Query("SELECT * FROM conversions ORDER BY timeStamp DESC LIMIT 1")
    suspend fun getLatestConversionWithCurrencies(): ConversionWithCurrencies?


    @Transaction
    @Query("SELECT * FROM conversions WHERE `from`= :fromSymbol AND `to` = :toSymbol")
    suspend fun getLatestConversionWithCurrenciesBySymbols(fromSymbol:String, toSymbol:String): ConversionWithCurrencies?
}