package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.model.ConvertedRoot
import com.alexeyyuditsky.exchange_rates.utils.currentDate
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApi {

    @GET("{date}/currencies/usd.json")
    suspend fun getCurrencies(@Path("date") date: String): ConvertedRoot

}