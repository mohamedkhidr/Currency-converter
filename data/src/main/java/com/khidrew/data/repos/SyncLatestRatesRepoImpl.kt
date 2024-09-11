package com.khidrew.data.repos

import android.util.Log
import com.khidrew.data.BuildConfig
import com.khidrew.data.dataSource.local.database.CurrencyDataBase
import com.khidrew.data.dataSource.local.database.databaseEntities.Currency
import com.khidrew.data.dataSource.remote.ApiService
import com.khidrew.data.dataSource.remote.dtos.CurrencyResponse
import com.khidrew.domain.apiStates.ApiStates
import com.khidrew.domain.repos.SyncLatestRatesRepo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SyncLatestRatesRepoImpl @Inject constructor(
    private val dataBase: CurrencyDataBase,
    private val apiService: ApiService
) :
    SyncLatestRatesRepo {
    override suspend fun syncLatestRates(): ApiStates {
        return try {
            val response = apiService.getLatestRates(BuildConfig.API_KEY)
            if (response.isSuccessful) {
                val data = response.body()
                insertDataUpdates(data)
                ApiStates.Success(data)
            } else {
                ApiStates.Failure(Throwable("${response.errorBody()} : ${response.code()}"))
            }
        } catch (e: IOException) {
            Log.e("API_ERROR", "Network Error: ${e.message}")
            ApiStates.Failure(e)
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Exception: ${e.code()}")
            ApiStates.Failure(e)
        } catch (e: Exception) {
            Log.e("API_ERROR", "Unknown Error: ${e.message}")
            ApiStates.Failure(e)
        }
    }

    private fun insertDataUpdates(data: CurrencyResponse?) {
        data?.rates?.map {
            dataBase.symbolsDao().insertSymbol(Currency(symbol = it.key, price = it.value))
        }

    }

}