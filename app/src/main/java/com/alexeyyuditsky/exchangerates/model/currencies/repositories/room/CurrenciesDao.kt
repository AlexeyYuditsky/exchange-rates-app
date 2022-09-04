package com.alexeyyuditsky.exchangerates.model.currencies.repositories.room

import androidx.room.*
import com.alexeyyuditsky.exchangerates.model.currencies.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesDao {

    @Query("select * from currencies where code in(:searchBy) limit :limit offset :offset")
    suspend fun getCurrencies(limit: Int, offset: Int, searchBy: List<String>): List<CurrencyDbEntity>

    @Insert(entity = CurrencyDbEntity::class)
    suspend fun insertCurrencies(currenciesList: List<CurrencyDbEntity>)

    @Update(entity = CurrencyDbEntity::class)
    suspend fun setIsFavoriteCurrency(tuple: UpdateCurrencyFavoriteFlagTuple)

    @Query("select count(*) = 0 from currencies")
    suspend fun currenciesTableIsEmpty(): Boolean

    @Update(entity = CurrencyDbEntity::class)
    suspend fun updateCurrencies(tuple: List<UpdateCurrencyValueTuple>)

    @Query("select * from currencies where isFavorite = 1")
    fun getFavoriteCurrencies(): Flow<List<Currency>>

    @Query("select * from currencies")
    fun getCurrencies(): List<Currency>

    @Query("select * from currencies")
    fun getConverterCurrencies(): Flow<List<Currency>>

}