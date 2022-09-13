package com.alexeyyuditsky.exchangerates.model.network

import com.alexeyyuditsky.exchangerates.model.currencies.repositories.room.CurrenciesDao
import com.alexeyyuditsky.exchangerates.model.currencies.repositories.room.CurrencyDbEntity
import com.alexeyyuditsky.exchangerates.model.currencies.repositories.room.UpdateCurrencyValueTuple
import com.alexeyyuditsky.exchangerates.utils.CURRENCY_FORMAT
import com.alexeyyuditsky.exchangerates.utils.getLatestDate
import com.alexeyyuditsky.exchangerates.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitCurrenciesSource @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    retrofit: Retrofit
) : CurrenciesSource {

    private val currenciesApi = retrofit.create(CurrenciesApi::class.java)

    private val currencyCurrentValues = mutableListOf<CurrencyNetworkEntity>()
    private val currencyYesterdayValues = mutableListOf<CurrencyNetworkEntity>()

    override suspend fun getCurrenciesFromNetwork(date: Int): Boolean = withContext(Dispatchers.IO) {
        val todayCurrencies = async { currenciesApi.getCurrencies(getLatestDate(date)).currencies }
        val yesterdayCurrencies = async { currenciesApi.getCurrencies(getLatestDate(date - 1)).currencies }
        currencyCurrentValues.addAll(todayCurrencies.await())
        currencyYesterdayValues.addAll(yesterdayCurrencies.await())

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
                isFavorite = currency.name == "USD" || currency.name == "EUR"
            )
            currencyList.add(currencyDbEntity)
        }
        currenciesDao.insertCurrencies(currencyList)
    }

    private fun calculateValues(todayValue: String, yesterdayValue: String): String {
        return CURRENCY_FORMAT.format(Locale.ROOT, todayValue.toBigDecimal() - yesterdayValue.toBigDecimal())
    }

}