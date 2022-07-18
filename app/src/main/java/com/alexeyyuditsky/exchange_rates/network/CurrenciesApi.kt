package com.alexeyyuditsky.exchange_rates.network

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrenciesApi {

    @GET("{date}/currencies/usd.json")
    suspend fun getCurrencies(@Path("date") date: String): ConvertedRoot

}