package com.alexeyyuditsky.exchange_rates.model.currencies

data class ConverterCurrency(
    val code: String,
    val valueToday: String = "0",
    var valueShow: String = "0"
)