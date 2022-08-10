package com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room

import androidx.room.*
import com.alexeyyuditsky.exchange_rates.room.entities.CryptocurrencyDbEntity

@Dao
interface CurrenciesDao {

    @Query("select * from currencies where code in(:searchBy) limit :limit offset :offset")
    suspend fun getCurrencies(limit: Int, offset: Int, searchBy: List<String>): List<CurrencyDbEntity>

    @Insert(entity = CurrencyDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currenciesList: List<CurrencyDbEntity>)

    /*@Insert(entity = CryptocurrencyDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptocurrencies(cryptocurrenciesList: List<CurrencyDbEntity>)*/

    @Update(entity = CurrencyDbEntity::class)
    suspend fun setIsFavorite(tuple: UpdateCurrencyFavoriteFlagTuple)

    @Query("select count(*) from currencies")
    suspend fun countRows(): Int

    @Update(entity = CurrencyDbEntity::class)
    suspend fun updateCurrencies(tuple: List<UpdateCurrencyValueTuple>)

}