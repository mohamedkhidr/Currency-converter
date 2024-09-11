package com.khidrew.data.repos

import android.util.Log
import com.khidrew.data.dataSource.local.database.CurrencyDataBase
import com.khidrew.data.mappers.ConversionMapper
import com.khidrew.data.utils.TimeUtils.getFourDaysAgoTimestamp
import com.khidrew.domain.entities.ConversionModel
import com.khidrew.domain.repos.ConversionRepo
import javax.inject.Inject

class ConversionsRepoImpl @Inject constructor(private val dataBase: CurrencyDataBase) :
    ConversionRepo {
    override suspend fun getConversions(): List<ConversionModel> {
        return dataBase.conversionDao().get4DaysConversions(getFourDaysAgoTimestamp(), System.currentTimeMillis())
            .map {conv ->
                ConversionMapper.mapConversionEntityToConversionModel(conv)

            }
    }
}