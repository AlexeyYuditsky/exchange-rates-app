package com.alexeyyuditsky.exchange_rates.network

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
    retrofit: Retrofit
) : CurrenciesSource {

    private val currenciesApi = retrofit.create(CurrenciesApi::class.java)

    override suspend fun getCurrencies(): ConvertedRoot {
        val currencyNames = currenciesApi.getCurrencyNames()
        val currencyCurrentValues = currenciesApi.getCurrencies(getCurrentDate()).currencies
        val currencyYesterdayValues = currenciesApi.getCurrencies(getYesterdayDate()).currencies

        val currencyListForDb = getCurrencyListForDb(currencyNames, currencyCurrentValues, currencyYesterdayValues)
        val currencyList = currencyListForDb.filter { !it.isCryptocurrency }
        val cryptocurrencyList = currencyListForDb.filter { it.isCryptocurrency }



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
    ): List<CurrencyForDb> {
        if (currencyNames.size != currencyCurrentValues.size)
            throw IllegalStateException("currencyNames and currencyCurrentValues lists are not equals")

        val list = mutableListOf<CurrencyForDb>()
        repeat(currencyNames.size) {
            list.add(
                CurrencyForDb(
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

    private data class CurrencyForDb(
        val shortName: String,
        val fullName: String,
        val valueToday: String,
        val valueTodayMinusYesterday: Float,
        val isCryptocurrency: Boolean
    )

}