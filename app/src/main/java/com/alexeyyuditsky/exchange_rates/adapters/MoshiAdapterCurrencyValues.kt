package com.alexeyyuditsky.exchange_rates.adapters

import com.alexeyyuditsky.exchange_rates.network.*
import com.alexeyyuditsky.exchange_rates.utils.FORMAT
import com.alexeyyuditsky.exchange_rates.utils.cryptocurrencyNames
import com.alexeyyuditsky.exchange_rates.utils.log
import com.squareup.moshi.FromJson
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
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
    ): List<CurrencyNetworkEntity> {
        val declaredMemberProperties = currencies::class.declaredMemberProperties
        val rubleExchangeRate = findRubleExchangeRate(declaredMemberProperties, currencies)

        val df = DecimalFormat("#.####")
        df.roundingMode = RoundingMode.FLOOR

        return declaredMemberProperties
            // exclude the ruble exchange rate from the list && exclude currency that are missing from the server
            .filter {
                it.name != "rub" && it.call(currencies) != null
            }
            .map {
                CurrencyNetworkEntity(
                    name = it.name.uppercase(),
                    value = FORMAT.format(Locale.ROOT, rubleExchangeRate / it.call(currencies).toString().toFloat()),
                    isCryptocurrency = cryptocurrencyNames.contains(it.name)
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