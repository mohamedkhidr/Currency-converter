package com.khidrew.currency.di

import com.khidrew.data.repos.SyncLatestRatesRepoImpl
import com.khidrew.domain.repos.ConversionBySymbolRepo
import com.khidrew.domain.repos.ConversionInsertionRepo
import com.khidrew.domain.repos.ConversionRepo
import com.khidrew.domain.repos.CurrenciesRepo
import com.khidrew.domain.repos.LatestConversionRepo
import com.khidrew.domain.repos.SyncLatestRatesRepo
import com.khidrew.domain.usecases.GetConversionBySymbols
import com.khidrew.domain.usecases.GetConversionsUseCase
import com.khidrew.domain.usecases.GetCurrenciesUseCase
import com.khidrew.domain.usecases.GetLatestConversionUseCase
import com.khidrew.domain.usecases.InsertOrUpdateConversionUseCase
import com.khidrew.domain.usecases.SyncLatestRatesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetConversionsUseCase(conversionsRepo: ConversionRepo): GetConversionsUseCase{
        return GetConversionsUseCase(conversionsRepo)
    }

    @Provides
    fun provideGetLatestConversionUseCase(latestConversionRepo: LatestConversionRepo): GetLatestConversionUseCase{
        return GetLatestConversionUseCase(latestConversionRepo)
    }

    @Provides
    fun provideInsertOrUpdateUseCase(conversionInsertionRepo: ConversionInsertionRepo): InsertOrUpdateConversionUseCase{
        return InsertOrUpdateConversionUseCase(conversionInsertionRepo)
    }

    @Provides
    fun provideLatestRatesUseCase(ratesRepo: SyncLatestRatesRepo): SyncLatestRatesUseCase{
        return SyncLatestRatesUseCase(ratesRepo)
    }

    @Provides
    fun provideGetCurrenciesUseCase(currenciesRepo: CurrenciesRepo): GetCurrenciesUseCase{
        return GetCurrenciesUseCase(currenciesRepo)
    }

    @Provides
    fun provideGetConversionBySymbolsUseCase(conversionBySymbolRepo: ConversionBySymbolRepo): GetConversionBySymbols{
        return GetConversionBySymbols(conversionBySymbolRepo)
    }
}