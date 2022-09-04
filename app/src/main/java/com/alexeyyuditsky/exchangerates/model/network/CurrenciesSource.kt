package com.alexeyyuditsky.exchangerates.model.network

interface CurrenciesSource {

    suspend fun getCurrenciesFromNetwork(): Boolean

    suspend fun insertCurrenciesIntoDatabase()

    suspend fun updateCurrenciesIntoDatabase()

}