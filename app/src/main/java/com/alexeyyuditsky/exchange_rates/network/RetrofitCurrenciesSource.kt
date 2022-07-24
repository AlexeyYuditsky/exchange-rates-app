package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.room.CurrenciesDao
import com.alexeyyuditsky.exchange_rates.room.entities.CurrencyDbEntity
import com.alexeyyuditsky.exchange_rates.utils.getCurrentDate
import com.alexeyyuditsky.exchange_rates.utils.getYesterdayDate
import com.alexeyyuditsky.exchange_rates.utils.log
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.min

@Singleton
class RetrofitCurrenciesSource @Inject constructor(
    retrofit: Retrofit,
    private val currenciesDao: CurrenciesDao
) : CurrenciesSource {

    private val currenciesApi = retrofit.create(CurrenciesApi::class.java)

    override suspend fun getCurrencies(): ConvertedRoot {
        val currencyNames = currenciesApi.getCurrencyNames()
        val currencyCurrentValues = currenciesApi.getCurrencies(getCurrentDate()).currencies
        val currencyYesterdayValues = currenciesApi.getCurrencies(getYesterdayDate()).currencies

        val currencyListForDb = getCurrencyListForDb(currencyNames, currencyCurrentValues, currencyYesterdayValues)
        val currencyList = currencyListForDb.filter { !it.isCryptocurrency }
        val cryptocurrencyList = currencyListForDb.filter { it.isCryptocurrency }

        currenciesDao.insertAllCurrencies(currencyList)

        val res = currenciesDao.getCurrencies()
        log(res)

        return try {
            currenciesApi.getCurrencies(getCurrentDate())
        } catch (e: HttpException) {
            currenciesApi.getCurrencies(getYesterdayDate())
        }
    }

    private fun getCurrencyListForDb(
        currencyNames: List<String>,
        currencyCurrentValues: List<Currency>,
        currencyYesterdayValues: List<Currency>
    ): List<CurrencyDbEntity> {
        if (currencyNames.size != currencyCurrentValues.size)
            throw IllegalStateException("currencyNames and currencyCurrentValues lists are not equals")

        val list = mutableListOf<CurrencyDbEntity>()
        repeat(currencyNames.size) {
            list.add(
                CurrencyDbEntity(
                    shortName = currencyCurrentValues[it].name,
                    fullName = currencyNames[it],
                    valueToday = currencyCurrentValues[it].value,
                    valueTodayMinusYesterday = currencyCurrentValues[it].value.toFloat() - currencyYesterdayValues[it].value.toFloat(),
                    isCryptocurrency = currencyCurrentValues[it].isCryptocurrency
                )
            )
        }
        return list
    }

}