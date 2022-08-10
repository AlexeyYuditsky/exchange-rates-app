package com.alexeyyuditsky.exchange_rates.model.currencies.repositories

import androidx.paging.PagingData
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {

    fun getPagedCurrencies(searchBy: List<String>): Flow<PagingData<Currency>>

    suspend fun setIsFavorite(currency: Currency, isFavorite: Boolean)

}