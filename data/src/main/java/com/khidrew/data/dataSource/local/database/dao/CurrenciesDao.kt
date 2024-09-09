package com.khidrew.data.dataSource.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khidrew.data.dataSource.local.database.databaseEntities.Currency
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSymbol(symbols: List<Currency>)

    @Query("SELECT * FROM currencies")
    fun getAllSymbols(): Flow<List<Currency>>

    @Query("SELECT * FROM currencies WHERE symbol = :symbol")
    fun getCurrencyBySymbol(symbol: String): Currency
}