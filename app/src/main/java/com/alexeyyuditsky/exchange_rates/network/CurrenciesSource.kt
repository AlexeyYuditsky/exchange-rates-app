package com.alexeyyuditsky.exchange_rates.network

import com.alexeyyuditsky.exchange_rates.room.entities.UICurrency

interface CurrenciesSource {

    suspend fun getCurrenciesFromNetwork()

    suspend fun insertCurrenciesIntoDatabase()

    suspend fun getCurrenciesFromDatabase(): List<UICurrency>

    fun getCurrenciesDate(): String

}