package com.alexeyyuditsky.exchange_rates.network

interface CurrenciesSource {

    suspend fun getCurrenciesFromNetwork(): Boolean

    suspend fun insertCurrenciesIntoDatabase()

    suspend fun updateCurrenciesIntoDatabase()

}