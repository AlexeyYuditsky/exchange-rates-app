package com.alexeyyuditsky.exchange_rates.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrenciesDao
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrencyDbEntity

@Database(
    version = 1,
    entities = [
        CurrencyDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCurrenciesDao(): CurrenciesDao

}