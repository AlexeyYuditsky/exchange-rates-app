package com.alexeyyuditsky.exchange_rates.adapters

import com.alexeyyuditsky.exchange_rates.network.*
import com.squareup.moshi.FromJson
import kotlin.reflect.full.declaredMemberProperties

class MoshiAdapterCurrencyNames {

    @FromJson
    fun fromJson(
        currencies: ResponseCurrencies
    ): List<String> {
        return createCurrencyNamesList(currencies)
    }

    private fun createCurrencyNamesList(
        currencies: ResponseCurrencies
    ): List<String> {
        val declaredMemberProperties = currencies::class.declaredMemberProperties

        return declaredMemberProperties
            .filter {
                // exclude the name of the ruble from the list && exclude currency that are missing from the server
                it.name != "rub" && it.call(currencies) != null
            }
            .map {
                it.call(currencies).toString()
            }
    }

}