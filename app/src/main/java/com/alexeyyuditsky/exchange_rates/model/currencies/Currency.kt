package com.alexeyyuditsky.exchange_rates.model.currencies

data class Currency(
    val code: String,
    val valueToday: String,
    val valueDifference: String,
    val isFavorite: Boolean
) {

    fun toConverterCurrency(): ConverterCurrency = ConverterCurrency(
        code = this.code,
        valueToday = this.valueToday
    )

}