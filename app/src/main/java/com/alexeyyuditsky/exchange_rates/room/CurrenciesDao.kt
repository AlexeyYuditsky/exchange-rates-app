package com.alexeyyuditsky.exchange_rates.room

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.*
import com.alexeyyuditsky.exchange_rates.room.entities.CurrencyDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesDao {

    @Insert(entity = CurrencyDbEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCurrencies(currenciesList: List<CurrencyDbEntity>)

   /* @Query("SELECT * FROM currencies")
    fun getCurrencies(): Flow<PagingData<CurrencyDbEntity>>*/

    @Query("DELETE FROM currencies")
    fun deleteAllCurrencies()

}