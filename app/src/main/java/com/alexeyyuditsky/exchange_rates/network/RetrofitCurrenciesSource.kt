package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrenciesDao
import com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room.CurrencyDbEntity
import com.alexeyyuditsky.exchange_rates.utils.FORMAT
import com.alexeyyuditsky.exchange_rates.utils.getCurrentDate
import com.alexeyyuditsky.exchange_rates.utils.getYesterdayDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    private lateinit var currencyNames: List<String>
    private lateinit var currencyCurrentValues: List<CurrencyNetworkEntity>
    private lateinit var currencyYesterdayValues: List<CurrencyNetworkEntity>

    override suspend fun getCurrenciesFromNetwork() = withContext(Dispatchers.IO) {
        currencyNames = currenciesApi.getCurrencyNames()
        currencyCurrentValues = currenciesApi.getCurrencies(getCurrentDate()).currencies
        currencyYesterdayValues = currenciesApi.getCurrencies(getYesterdayDate()).currencies

        insertCurrenciesIntoDatabase()
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

fun main() {
    val a = 0.123123f
    val b = 0.12312200f
    val c = FORMAT.format(Locale.ROOT, a.toBigDecimal() - b.toBigDecimal())
    println(c)


}