package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.utils.getCurrentDate
import com.alexeyyuditsky.exchange_rates.utils.getYesterdayDate
import com.alexeyyuditsky.exchange_rates.utils.log
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitCurrenciesSource @Inject constructor(
    retrofit: Retrofit
) : CurrenciesSource {

    private val currenciesApi = retrofit.create(CurrenciesApi::class.java)

    override suspend fun getCurrencies(): ConvertedRoot {
        val currencyNames = currenciesApi.getCurrencyNames()
        val currencyValues = currenciesApi.getCurrencies(getCurrentDate()).currencies

        val tempList = currencyNames.zip(currencyValues) { fullName, currency ->
            CurrencyForDb(
                shortName = currency.name,
                fullName = fullName,
                value = currency.value
            )
        }

        log(tempList)

        return try {
            currenciesApi.getCurrencies(getCurrentDate())
        } catch (e: HttpException) {
            currenciesApi.getCurrencies(getYesterdayDate())
        }
    }

    private data class CurrencyForDb(
        val shortName: String,
        val fullName: String,
        val value: String
    )

}