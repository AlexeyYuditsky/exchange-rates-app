package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrenciesDao
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrencyDbEntity
import com.alexeyyuditsky.exchange_rates.utils.FORMAT
import com.alexeyyuditsky.exchange_rates.utils.getLatestDate
import com.alexeyyuditsky.exchange_rates.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitCurrenciesSource @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    retrofit: Retrofit
) : CurrenciesSource {

    private val currenciesApi = retrofit.create(CurrenciesApi::class.java)

    private lateinit var currencyNames: List<String>
    private lateinit var currencyCurrentValues: List<CurrencyNetworkEntity>
    private lateinit var currencyYesterdayValues: List<CurrencyNetworkEntity>

    override suspend fun getCurrenciesFromNetwork() = withContext(Dispatchers.IO) {
        currencyNames = currenciesApi.getCurrencyNames()

        try {
            currencyCurrentValues = currenciesApi.getCurrencies(getLatestDate()).currencies
            currencyYesterdayValues = currenciesApi.getCurrencies(getLatestDate(-1)).currencies
        } catch (e: HttpException) {
            currencyCurrentValues = currenciesApi.getCurrencies(getLatestDate(-1)).currencies
            currencyYesterdayValues = currenciesApi.getCurrencies(getLatestDate(-2)).currencies
        }

        insertCurrenciesIntoDatabase()

        return@withContext true
    }

    override suspend fun insertCurrenciesIntoDatabase() = withContext(Dispatchers.IO) {
        val currencyList = mutableListOf<CurrencyDbEntity>()
        val cryptocurrencyList = mutableListOf<CurrencyDbEntity>()

        currencyCurrentValues.forEachIndexed { index, currency ->
            val currencyDbEntity = CurrencyDbEntity(
                id = 0,
                shortName = currency.name,
                fullName = currencyNames[index],
                valueToday = currency.value,
                valueDifference = calculateValues(currency.value, currencyYesterdayValues[index].value)
            )
            if (currency.isCryptocurrency) cryptocurrencyList.add(currencyDbEntity)
            else currencyList.add(currencyDbEntity)
        }

        currenciesDao.insertCurrencies(currencyList)
        currenciesDao.insertCryptocurrencies(cryptocurrencyList)
    }

    private fun calculateValues(todayValue: String, yesterdayValue: String): String {
        return FORMAT.format(Locale.ROOT, todayValue.toBigDecimal() - yesterdayValue.toBigDecimal())
    }

}