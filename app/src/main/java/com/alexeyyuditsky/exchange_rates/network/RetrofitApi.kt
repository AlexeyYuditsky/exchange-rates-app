package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.model.ConvertedRoot
import retrofit2.http.GET

interface RetrofitApi {
    @GET("usd.json")
    suspend fun getCurrencies(): ConvertedRoot
}