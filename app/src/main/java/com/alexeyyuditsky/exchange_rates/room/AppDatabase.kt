package com.alexeyyuditsky.exchange_rates.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexeyyuditsky.exchange_rates.room.entities.CryptocurrencyDbEntity
import com.alexeyyuditsky.exchange_rates.room.entities.CurrencyDbEntity

@Database(
    version = 1,
    entities = [
        CurrencyDbEntity::class,
        CryptocurrencyDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCurrenciesDao(): CurrenciesDao

}