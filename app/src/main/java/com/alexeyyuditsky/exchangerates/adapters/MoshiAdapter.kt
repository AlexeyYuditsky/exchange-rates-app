package com.alexeyyuditsky.exchangerates.adapters

import com.alexeyyuditsky.exchangerates.model.network.*
import com.alexeyyuditsky.exchangerates.utils.CURRENCY_FORMAT
import com.squareup.moshi.FromJson
import java.util.*
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

class MoshiAdapter {

    @FromJson
    fun fromJson(
        responseRoot: ResponseRoot
    ): ConvertedRoot = ConvertedRoot(
        date = responseRoot.date,
        currencies = createCurrenciesList(responseRoot.currencies)
    )

    private fun createCurrenciesList(
        currencies: ResponseCurrencies
    ): List<CurrencyNetworkEntity> {
        val declaredMemberProperties = currencies::class.declaredMemberProperties
        val rubleExchangeRate = findRubleExchangeRate(declaredMemberProperties, currencies)

        return declaredMemberProperties
            // exclude the ruble exchange rate from the list && exclude currency that are missing from the server
            .filter {
                it.name != "rub" && it.call(currencies) != null
            }
            .map {
                CurrencyNetworkEntity(
                    name = it.name.uppercase(),
                    value = CURRENCY_FORMAT.format(Locale.ROOT, rubleExchangeRate / it.call(currencies).toString().toFloat()),
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