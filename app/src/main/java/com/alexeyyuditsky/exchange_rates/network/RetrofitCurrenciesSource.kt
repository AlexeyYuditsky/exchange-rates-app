package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.utils.getCurrentDate
import com.alexeyyuditsky.exchange_rates.utils.getYesterdayDate
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
        return try {
            currenciesApi.getCurrencies(getCurrentDate())
        } catch (e: HttpException) {
            currenciesApi.getCurrencies(getYesterdayDate())
        }
    }

}