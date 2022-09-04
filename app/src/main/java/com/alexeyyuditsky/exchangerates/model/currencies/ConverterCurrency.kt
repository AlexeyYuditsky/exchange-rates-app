package com.alexeyyuditsky.exchangerates.model.currencies

data class ConverterCurrency(
    val code: String,
    val valueToday: String = "0",
    var valueShow: String = "0"
)