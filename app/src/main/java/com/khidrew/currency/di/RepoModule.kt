package com.khidrew.currency.di

import com.khidrew.data.dataSource.local.database.CurrencyDataBase
import com.khidrew.data.dataSource.remote.ApiService
import com.khidrew.data.repos.ConversionInsertionRepoImpl
import com.khidrew.data.repos.ConversionsRepoImpl
import com.khidrew.data.repos.CurrenciesRepoImpl
import com.khidrew.data.repos.LatestConversionRepoImpl
import com.khidrew.data.repos.SyncLatestRatesRepoImpl
import com.khidrew.domain.repos.ConversionInsertionRepo
import com.khidrew.domain.repos.ConversionRepo
import com.khidrew.domain.repos.CurrenciesRepo
import com.khidrew.domain.repos.LatestConversionRepo
import com.khidrew.domain.repos.SyncLatestRatesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun getConversionsRepo(dataBase: CurrencyDataBase): ConversionRepo {
        return ConversionsRepoImpl(dataBase)
    }

    @Provides
    fun getLatestConversionRepo(dataBase: CurrencyDataBase): LatestConversionRepo {
        return LatestConversionRepoImpl(dataBase)
    }

    @Provides
    fun getConversionInsertionRepo(dataBase: CurrencyDataBase): ConversionInsertionRepo {
        return ConversionInsertionRepoImpl(dataBase)
    }

    @Provides
    fun getSyncRatesRepo(apiService: ApiService, dataBase: CurrencyDataBase): SyncLatestRatesRepo {
        return SyncLatestRatesRepoImpl(dataBase, apiService)
    }

    @Provides
    fun getCurrenciesRepo( dataBase: CurrencyDataBase): CurrenciesRepo {
        return CurrenciesRepoImpl(dataBase)
    }



}