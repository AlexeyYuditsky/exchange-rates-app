package com.alexeyyuditsky.exchangerates.di

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.exchangerates.model.AppDatabase
import com.alexeyyuditsky.exchangerates.model.currencies.repositories.room.CurrenciesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }

    @Singleton
    @Provides
    fun provideCurrenciesDao(
        database: AppDatabase
    ): CurrenciesDao {
        return database.getCurrenciesDao()
    }

    private companion object {
        const val DB_NAME = "currencies.db"
    }

}