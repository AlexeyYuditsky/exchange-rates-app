package com.alexeyyuditsky.exchange_rates.network

interface CurrenciesSource {

    suspend fun getCurrenciesFromNetwork()

    suspend fun insertCurrenciesIntoDatabase()

}