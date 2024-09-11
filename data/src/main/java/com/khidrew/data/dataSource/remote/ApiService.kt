package com.khidrew.data.dataSource.remote

import com.khidrew.data.dataSource.local.database.databaseEntities.Currency
import com.khidrew.data.dataSource.remote.dtos.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

@GET("/latest")
suspend fun getLatestRates(@Query("access_key") apiKey:String):Response<CurrencyResponse>


@GET("/convert")
suspend fun convert(@Query("from") fromSymbol: String,
                    @Query("to") toSymbol:String ,
                    @Query("amount") fromAmount: Double): Double
}