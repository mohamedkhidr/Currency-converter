package com.khidrew.data.mappers

import com.khidrew.data.dataSource.local.database.databaseEntities.Conversion
import com.khidrew.domain.entities.ConversionModel

object ConversionMapper {

    fun mapConversionEntityToConversionModel(conversionEntity: Conversion): ConversionModel =
        ConversionModel(
            conversionEntity.id,
            conversionEntity.from,
            conversionEntity.to,
            conversionEntity.fromAmount,
            conversionEntity.toAmount,
            conversionEntity.timeStamp
        )


    fun mapConversionModelToConversionEntity(conversionModel: ConversionModel): Conversion =
        Conversion(
            conversionModel.id,
            conversionModel.from,
            conversionModel.to,
            conversionModel.fromAmount,
            conversionModel.toAmount,
            conversionModel.timeStamp
        )
}