package com.khidrew.domain.repos

import com.khidrew.domain.entities.ConversionModel

interface ConversionRepo {
    suspend fun getConversions(): List<ConversionModel>
}