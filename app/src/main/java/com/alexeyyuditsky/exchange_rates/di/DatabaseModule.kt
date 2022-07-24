package com.alexeyyuditsky.exchange_rates.di

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.exchange_rates.room.AppDatabase
import com.alexeyyuditsky.exchange_rates.room.CurrenciesDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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