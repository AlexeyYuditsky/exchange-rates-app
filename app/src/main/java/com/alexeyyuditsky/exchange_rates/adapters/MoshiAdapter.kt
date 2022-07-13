package com.alexeyyuditsky.exchange_rates.adapters

import com.alexeyyuditsky.exchange_rates.network.ConvertedRoot
import com.alexeyyuditsky.exchange_rates.network.Currency
import com.alexeyyuditsky.exchange_rates.network.ResponseCurrencies
import com.alexeyyuditsky.exchange_rates.network.ResponseRoot
import com.alexeyyuditsky.exchange_rates.utils.log
import com.squareup.moshi.FromJson
import kotlin.reflect.full.declaredMemberProperties

class MoshiAdapter {

    @FromJson
    private fun fromJson(responseRoot: ResponseRoot): ConvertedRoot {
        return ConvertedRoot(
            date = responseRoot.date,
            currencies = createCurrenciesList(responseRoot.currencies)
        )
    }

    private fun createCurrenciesList(currencies: ResponseCurrencies): List<Currency> {
        val rubleExchangeRate = findRubleExchangeRate(currencies)

        return currencies::class.declaredMemberProperties
            // exclude the ruble exchange rate from the list && exclude currency that are missing from the server
            .filter { it.name != "rub" && it.call(currencies).toString() != "0.0" }
            .map {
                Currency(
                    name = it.name,
                    value = rubleExchangeRate / it.call(currencies).toString().toFloat()
                )
            }
    }

    private fun findRubleExchangeRate(currencies: ResponseCurrencies): Float {
        return currencies::class.declaredMemberProperties
            .first { it.name == "rub" }
            .call(currencies)
            .toString()
            .toFloat()
    }

}