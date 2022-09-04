package com.alexeyyuditsky.exchange_rates.model.currencies.repositories

import androidx.paging.PagingData
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {

    fun getPagedCurrencies(searchBy: List<String>): Flow<PagingData<Currency>>

    suspend fun setIsFavoriteCurrency(currency: Currency, isFavorite: Boolean)

    fun getFavoriteCurrencies(): Flow<List<Currency>>

    fun getConverterCurrencies(): Flow<List<Currency>>

    suspend fun getCurrencies(): List<Currency>

}