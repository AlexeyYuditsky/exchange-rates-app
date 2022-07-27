package com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room

import androidx.room.*
import com.alexeyyuditsky.exchange_rates.room.entities.CryptocurrencyDbEntity

@Dao
interface CurrenciesDao {

    @Query(
        "SELECT * FROM currencies " +
                "WHERE :searchBy = '' OR fullName LIKE '%' || :searchBy || '%' " + // search substring
                // "ORDER BY name " +  // sort by user name
                "LIMIT :limit OFFSET :offset"
    ) // return max :limit number of users starting from :offset position
    suspend fun getCurrencies(limit: Int, offset: Int, searchBy: String = ""): List<CurrencyDbEntity>

    @Query(
        "SELECT * FROM currencies " +
                "WHERE shortName = 'aed'"
    )
    suspend fun getFirstCurrency(): CurrencyDbEntity

    @Query("SELECT * FROM cryptocurrencies")
    suspend fun getCryptocurrencies(): List<CryptocurrencyDbEntity>

    @Insert(entity = CurrencyDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currenciesList: List<CurrencyDbEntity>): List<Long>

    @Insert(entity = CryptocurrencyDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptocurrencies(cryptocurrenciesList: List<CurrencyDbEntity>)

}