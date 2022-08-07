package com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexeyyuditsky.exchange_rates.model.currencies.CurrenciesPageLoader
import com.alexeyyuditsky.exchange_rates.model.currencies.CurrenciesPagingSource
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.CurrenciesRepository
import com.alexeyyuditsky.exchange_rates.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomCurrenciesRepository @Inject constructor(
    private val currenciesDao: CurrenciesDao
) : CurrenciesRepository {

    override fun getPagedCurrencies(searchBy: List<String>): Flow<PagingData<Currency>> {
        val loader: CurrenciesPageLoader = { pageIndex, pageSize ->
            getCurrencies(pageIndex, pageSize, searchBy)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PAGE_SIZE / 3,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { CurrenciesPagingSource(loader) }
        ).flow
    }

    private suspend fun getCurrencies(pageIndex: Int, pageSize: Int, searchBy: List<String>): List<Currency> =
        withContext(Dispatchers.IO) {
            // calculate offset value required by DAO
            val offset = pageIndex * pageSize

            // get page
            val currencies = currenciesDao.getCurrencies(pageSize, offset, searchBy)

            // map CurrencyDbEntity to UICurrency
            return@withContext currencies.map(CurrencyDbEntity::toCurrency)
        }

    private companion object {
        const val PAGE_SIZE = 30
    }

}