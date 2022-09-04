package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrenciesDao
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrencyDbEntity
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.UpdateCurrencyValueTuple
import com.alexeyyuditsky.exchange_rates.utils.FORMAT
import com.alexeyyuditsky.exchange_rates.utils.getLatestDate
import com.alexeyyuditsky.exchange_rates.utils.log
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitCurrenciesSource @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    retrofit: Retrofit
) : CurrenciesSource {

    private val currenciesApi = retrofit.create(CurrenciesApi::class.java)

    private lateinit var currencyCurrentValues: List<CurrencyNetworkEntity>
    private lateinit var currencyYesterdayValues: List<CurrencyNetworkEntity>

    override suspend fun getCurrenciesFromNetwork(): Boolean = withContext(Dispatchers.IO) {
        try {
            awaitAll(
                async {
                    currencyCurrentValues = currenciesApi.getCurrencies(getLatestDate()).currencies
                },
                async {
                    currencyYesterdayValues = currenciesApi.getCurrencies(getLatestDate(-1)).currencies
                }
            )
        } catch (e: HttpException) {
            awaitAll(
                async {
                    currencyCurrentValues = currenciesApi.getCurrencies(getLatestDate(-1)).currencies
                },
                async {
                    currencyYesterdayValues = currenciesApi.getCurrencies(getLatestDate(-2)).currencies
                }
            )
        }

        if (currenciesDao.currenciesTableIsEmpty())
            insertCurrenciesIntoDatabase()
        else
            updateCurrenciesIntoDatabase()

        return@withContext true
    }

    override suspend fun updateCurrenciesIntoDatabase() {
        val currencyList = mutableListOf<UpdateCurrencyValueTuple>()

        currencyCurrentValues.forEachIndexed { index, currency ->
            val updateCurrencyValueTuple = UpdateCurrencyValueTuple(
                code = currency.name,
                valueToday = currency.value,
                valueDifference = calculateValues(currency.value, currencyYesterdayValues[index].value)
            )
            currencyList.add(updateCurrencyValueTuple)
        }
        currenciesDao.updateCurrencies(currencyList)
    }

    override suspend fun insertCurrenciesIntoDatabase() = withContext(Dispatchers.IO) {
        val currencyList = mutableListOf<CurrencyDbEntity>()

        currencyCurrentValues.forEachIndexed { index, currency ->
            val currencyDbEntity = CurrencyDbEntity(
                code = currency.name,
                valueToday = currency.value,
                valueDifference = calculateValues(currency.value, currencyYesterdayValues[index].value),
                isFavorite = currency.name == "USD" || currency.name == "EUR" || currency.name == "GBP"
            )
            currencyList.add(currencyDbEntity)
        }
        currenciesDao.insertCurrencies(currencyList)
    }

    private fun calculateValues(todayValue: String, yesterdayValue: String): String {
        return FORMAT.format(Locale.ROOT, todayValue.toBigDecimal() - yesterdayValue.toBigDecimal())
    }

}