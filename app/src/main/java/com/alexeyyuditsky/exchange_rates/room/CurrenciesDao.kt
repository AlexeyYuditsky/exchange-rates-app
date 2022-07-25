package com.alexeyyuditsky.exchange_rates.room

import androidx.room.*
import com.alexeyyuditsky.exchange_rates.room.entities.CryptocurrencyDbEntity
import com.alexeyyuditsky.exchange_rates.room.entities.CurrencyDbEntity

@Dao
interface CurrenciesDao {

    @Insert(entity = CurrencyDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrencies(currenciesList: List<CurrencyDbEntity>)

    @Insert(entity = CryptocurrencyDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCryptocurrencies(cryptocurrenciesList: List<CurrencyDbEntity>)

    @Query("SELECT * FROM currencies")
    suspend fun getCurrencies(): List<CurrencyDbEntity>

    @Query("SELECT * FROM cryptocurrencies")
    suspend fun getCryptocurrencies(): List<CryptocurrencyDbEntity>

}