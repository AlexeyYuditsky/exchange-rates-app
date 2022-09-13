package com.alexeyyuditsky.exchangerates.model.network

interface CurrenciesSource {

    suspend fun getCurrenciesFromNetwork(date: Int): Boolean

    suspend fun insertCurrenciesIntoDatabase()

    suspend fun updateCurrenciesIntoDatabase()

}