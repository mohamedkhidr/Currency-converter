package com.khidrew.domain.repos

import com.khidrew.domain.entities.ConversionModel

interface ConversionBySymbolRepo {
    suspend fun getConversionBySymbol(fromSymbol:String, toSymbol:String) : ConversionModel?
}