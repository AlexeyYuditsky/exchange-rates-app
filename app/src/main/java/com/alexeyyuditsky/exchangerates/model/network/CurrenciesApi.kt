package com.alexeyyuditsky.exchangerates.model.network

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrenciesApi {

    @GET("currency-api@{date}/v1/currencies/usd.json")
    suspend fun getCurrencies(@Path("date") date: String): ConvertedRoot

}