package com.khidrew.data.dataSource.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khidrew.data.dataSource.local.database.databaseEntities.Conversion
@Dao
interface ConversionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(conversion: Conversion)


    @Query("SELECT * FROM conversions WHERE timeStamp >= :fourDaysAgoTimestamp ORDER BY timeStamp DESC")
    fun get4DaysConversions(fourDaysAgoTimestamp: Long):List<Conversion>
}