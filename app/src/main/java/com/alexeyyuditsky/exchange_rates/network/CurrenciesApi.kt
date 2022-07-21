package com.alexeyyuditsky.exchange_rates.network

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrenciesApi {

    @GET("{date}/currencies/usd.min.json")
    suspend fun getCurrencies(@Path("date") date: String): ConvertedRoot

    @GET("latest/currencies.min.json")
    suspend fun getCurrencyNames(): ResponseCurrencies

}