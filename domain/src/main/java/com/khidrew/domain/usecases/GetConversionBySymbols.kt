package com.khidrew.domain.usecases

import com.khidrew.domain.repos.ConversionBySymbolRepo

class GetConversionBySymbols(private val conversionBySymbolRepo: ConversionBySymbolRepo) {
    suspend operator fun invoke(fromSymbol: String, toSymbol: String) =
        conversionBySymbolRepo.getConversionBySymbol(fromSymbol, toSymbol)
}