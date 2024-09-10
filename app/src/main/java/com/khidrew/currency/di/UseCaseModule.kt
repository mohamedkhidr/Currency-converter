package com.khidrew.currency.di

import com.khidrew.domain.repos.ConversionInsertionRepo
import com.khidrew.domain.repos.ConversionRepo
import com.khidrew.domain.repos.LatestConversionRepo
import com.khidrew.domain.usecases.GetConversionsUseCase
import com.khidrew.domain.usecases.GetLatestConversion
import com.khidrew.domain.usecases.InsertOrUpdateConversion
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
    fun provideGetLatestConversionUseCase(latestConversionRepo: LatestConversionRepo): GetLatestConversion{
        return GetLatestConversion(latestConversionRepo)
    }

    @Provides
    fun provideInsertOrUpdateUseCase(conversionInsertionRepo: ConversionInsertionRepo): InsertOrUpdateConversion{
        return InsertOrUpdateConversion(conversionInsertionRepo)
    }
}