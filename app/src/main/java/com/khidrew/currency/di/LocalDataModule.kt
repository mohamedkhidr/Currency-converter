package com.khidrew.currency.di

import android.content.Context
import androidx.room.Room
import com.khidrew.data.dataSource.local.database.CurrencyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    fun getDatabaseInstance(@ApplicationContext context: Context): CurrencyDataBase {
        return Room.databaseBuilder(context, CurrencyDataBase::class.java, "currency")
            .fallbackToDestructiveMigrationFrom()
            .build()

    }


}