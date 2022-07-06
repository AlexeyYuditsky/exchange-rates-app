package com.alexeyyuditsky.exchange_rates.adapters

import com.alexeyyuditsky.exchange_rates.model.ConvertedRoot
import com.alexeyyuditsky.exchange_rates.model.Currency
import com.alexeyyuditsky.exchange_rates.model.ResponseRoot
import com.squareup.moshi.FromJson
import kotlin.reflect.full.declaredMemberProperties

class MoshiAdapter {

    @FromJson
    fun fromJson(responseRoot: ResponseRoot): ConvertedRoot {
        val namePropertiesList = mutableListOf<String>()
        val valuePropertiesList = mutableListOf<Float>()

        val properties = responseRoot.currencies::class
        properties.declaredMemberProperties.forEach {
            namePropertiesList.add(it.name)
            valuePropertiesList.add((it.call(responseRoot.currencies) as Float))
        }

        val currenciesList = mutableListOf<Currency>()

        repeat(namePropertiesList.size) {
            // позволяет не отображать валюту которой вдруг не стало на сервере
            if (valuePropertiesList[it] == 0f) return@repeat

            currenciesList.add(
                Currency(
                    name = namePropertiesList[it],
                    value = valuePropertiesList[it]
                )
            )
        }

        return ConvertedRoot(
            date = responseRoot.date,
            currencies = currenciesList
        )
    }

}