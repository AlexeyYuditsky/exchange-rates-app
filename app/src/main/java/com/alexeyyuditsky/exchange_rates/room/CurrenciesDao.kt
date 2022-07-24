package com.alexeyyuditsky.exchange_rates.room

import androidx.room.*
import com.alexeyyuditsky.exchange_rates.room.entities.CurrencyDbEntity

@Dao
interface CurrenciesDao {

    @Insert(entity = CurrencyDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrencies(currenciesList: List<CurrencyDbEntity>)

    @Query("SELECT * FROM currencies")
    suspend fun getCurrencies(): List<CurrencyDbEntity>

    @Query("DELETE FROM currencies")
    fun deleteAllCurrencies()

}