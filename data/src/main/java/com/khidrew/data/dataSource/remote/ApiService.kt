package com.khidrew.data.dataSource.remote

import com.khidrew.data.dataSource.local.database.databaseEntities.Currency
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

@GET("/latest")
suspend fun getAllSymbols(@Query("access_key") apiKey:String):List<Currency>


@GET("/convert")
suspend fun convert(@Query("from") fromSymbol: String,
                    @Query("to") toSymbol:String ,
                    @Query("amount") fromAmount: Double): Double
}