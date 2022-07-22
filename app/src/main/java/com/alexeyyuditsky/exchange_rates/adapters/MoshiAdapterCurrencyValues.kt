package com.alexeyyuditsky.exchange_rates.adapters

import com.alexeyyuditsky.exchange_rates.network.*
import com.squareup.moshi.FromJson
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

class MoshiAdapterCurrencyValues {

    @FromJson
    fun fromJson(
        responseRoot: ResponseRoot
    ): ConvertedRoot = ConvertedRoot(
        date = responseRoot.date,
        currencies = createCurrenciesList(responseRoot.currencies)
    )

    private fun createCurrenciesList(
        currencies: ResponseCurrencies
    ): List<Currency> {
        val declaredMemberProperties = currencies::class.declaredMemberProperties
        val rubleExchangeRate = findRubleExchangeRate(declaredMemberProperties, currencies)

        return declaredMemberProperties
            // exclude the ruble exchange rate from the list && exclude currency that are missing from the server
            .filter {
                it.name != "rub" && it.call(currencies) != null
            }
            .map {
                Currency(
                    name = it.name,
                    value = (rubleExchangeRate / it.call(currencies).toString().toFloat()).toString()
                )
            }
    }

    private fun findRubleExchangeRate(
        declaredMemberProperties: Collection<KProperty1<out ResponseCurrencies, *>>,
        currencies: ResponseCurrencies
    ): Float {
        return declaredMemberProperties
            .first { it.name == "rub" }
            .call(currencies)
            .toString()
            .toFloat()
    }

}