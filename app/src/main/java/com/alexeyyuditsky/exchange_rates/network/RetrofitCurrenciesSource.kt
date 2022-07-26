package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.room.CurrenciesDao
import com.alexeyyuditsky.exchange_rates.room.entities.CurrencyDbEntity
import com.alexeyyuditsky.exchange_rates.utils.getCurrentDate
import com.alexeyyuditsky.exchange_rates.utils.getYesterdayDate
import com.alexeyyuditsky.exchange_rates.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.ceil
import kotlin.math.round

@Singleton
class RetrofitCurrenciesSource @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    retrofit: Retrofit
) : CurrenciesSource {

    private val currenciesApi = retrofit.create(CurrenciesApi::class.java)

    private lateinit var currencyNames: List<String>
    private lateinit var currencyCurrentValues: List<Currency>
    private lateinit var currencyYesterdayValues: List<Currency>

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
                shortName = currency.name,
                fullName = currencyNames[index],
                valueToday = currency.value,
                valueTodayMinusYesterday = calculateValues(
                    currency.name,
                    currency.value.toFloat(),
                    currencyYesterdayValues[index].value.toFloat()
                )
            )
            if (currency.isCryptocurrency) cryptocurrencyList.add(currencyDbEntity)
            else currencyList.add(currencyDbEntity)
        }

        currenciesDao.insertAllCurrencies(currencyList)
        currenciesDao.insertAllCryptocurrencies(cryptocurrencyList)
    }

    override suspend fun getCurrenciesFromDatabase() = withContext(Dispatchers.IO) {
        return@withContext currenciesDao.getCurrencies().map { it.toUICurrency() }
    }

    override suspend fun getCurrenciesDate(): String = withContext(Dispatchers.IO) {
        return@withContext currenciesApi.getCurrencies(getCurrentDate()).date
    }

    private fun calculateValues(currency: String, todayValue: Float, yesterdayValue: Float): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(todayValue - yesterdayValue)
    }

}